package com.zcomini.backend.testify.service;

import com.zcomini.backend.testify.entity.ApiConfig;
import com.zcomini.backend.testify.repository.ApiConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApiConfigService {

    private final ApiConfigRepository apiConfigRepository;

    public List<ApiConfig> getAllConfigs() {
        return apiConfigRepository.findAll();
    }

    public ApiConfig getConfigById(UUID id) {
        return apiConfigRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Config not found"));
    }

    @Transactional
    public ApiConfig createConfig(ApiConfig config) {
        return apiConfigRepository.save(config);
    }

    @Transactional
    public ApiConfig updateConfig(UUID id, ApiConfig updateData) {
        ApiConfig existing = getConfigById(id);
        existing.setName(updateData.getName());
        existing.setBaseUrl(updateData.getBaseUrl());
        existing.setConfig(updateData.getConfig());
        return apiConfigRepository.save(existing);
    }

    @Transactional
    public void deleteConfig(UUID id) {
        apiConfigRepository.deleteById(id);
    }
}
