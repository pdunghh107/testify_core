package com.zcomini.backend.shared.web;

import com.zcomini.backend.shared.api.ApiError;
import com.zcomini.backend.shared.api.ApiResponse;
import com.zcomini.backend.shared.api.PageResponse;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.List;

@RestControllerAdvice
public class ApiEnvelopeResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body == null || selectedContentType == null || !MediaType.APPLICATION_JSON.isCompatibleWith(selectedContentType)) {
            return body;
        }
        if (body instanceof ApiResponse<?> || body instanceof ApiError) {
            return body;
        }
        if (body instanceof Page<?> page) {
            return ApiResponse.paged(PageResponse.from(page));
        }
        if (body instanceof PageResponse<?> pageResponse) {
            return ApiResponse.paged(pageResponse);
        }

        PageResponse<?> adaptedProductPage = adaptProductPage(body);
        if (adaptedProductPage != null) {
            return ApiResponse.paged(adaptedProductPage);
        }

        int status = response instanceof ServletServerHttpResponse servletResponse
                ? servletResponse.getServletResponse().getStatus()
                : 200;

        if (status == 201) {
            return ApiResponse.created(body);
        }

        return ApiResponse.ok(body);
    }

    @SuppressWarnings("unchecked")
    private PageResponse<?> adaptProductPage(Object body) {
        if (!"com.zcomini.backend.product.dto.PageResponse".equals(body.getClass().getName())) {
            return null;
        }
        try {
            Method content = body.getClass().getMethod("content");
            Method page = body.getClass().getMethod("page");
            Method size = body.getClass().getMethod("size");
            Method totalElements = body.getClass().getMethod("totalElements");
            Method totalPages = body.getClass().getMethod("totalPages");
            return new PageResponse<>(
                    (List<Object>) content.invoke(body),
                    (Integer) page.invoke(body),
                    (Integer) size.invoke(body),
                    (Long) totalElements.invoke(body),
                    (Integer) totalPages.invoke(body)
            );
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException("Cannot adapt product PageResponse to shared PageResponse", ex);
        }
    }
}
