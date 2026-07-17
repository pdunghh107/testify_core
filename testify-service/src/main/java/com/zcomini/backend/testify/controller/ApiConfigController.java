package com.zcomini.backend.testify.controller;

import com.zcomini.backend.testify.entity.ApiConfig;
import com.zcomini.backend.testify.service.ApiConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/configs")
@RequiredArgsConstructor
public class ApiConfigController {

    private final ApiConfigService apiConfigService;

    @GetMapping
    public ResponseEntity<List<ApiConfig>> getAllConfigs() {
        return ResponseEntity.ok(apiConfigService.getAllConfigs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiConfig> getConfigById(@PathVariable UUID id) {
        return ResponseEntity.ok(apiConfigService.getConfigById(id));
    }

    @PostMapping
    public ResponseEntity<ApiConfig> createConfig(@RequestBody ApiConfig config) {
        return new ResponseEntity<>(apiConfigService.createConfig(config), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiConfig> updateConfig(@PathVariable UUID id, @RequestBody ApiConfig config) {
        return ResponseEntity.ok(apiConfigService.updateConfig(id, config));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConfig(@PathVariable UUID id) {
        apiConfigService.deleteConfig(id);
        return ResponseEntity.noContent().build();
    }
}
