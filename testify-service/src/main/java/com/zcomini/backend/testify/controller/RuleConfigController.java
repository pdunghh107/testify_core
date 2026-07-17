package com.zcomini.backend.testify.controller;

import com.zcomini.backend.testify.entity.RuleConfig;
import com.zcomini.backend.testify.service.RuleConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rule-configs")
@RequiredArgsConstructor
public class RuleConfigController {

    private final RuleConfigService ruleConfigService;

    @GetMapping
    public ResponseEntity<List<RuleConfig>> getAllRuleConfigs() {
        return ResponseEntity.ok(ruleConfigService.getAllRuleConfigs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RuleConfig> getRuleConfigById(@PathVariable UUID id) {
        return ResponseEntity.ok(ruleConfigService.getRuleConfigById(id));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<RuleConfig> getRuleConfigByCode(@PathVariable String code) {
        return ResponseEntity.ok(ruleConfigService.getRuleConfigByCode(code));
    }

    @PostMapping
    public ResponseEntity<RuleConfig> createRuleConfig(@RequestBody RuleConfig ruleConfig) {
        return ResponseEntity.ok(ruleConfigService.createRuleConfig(ruleConfig));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RuleConfig> updateRuleConfig(@PathVariable UUID id, @RequestBody RuleConfig ruleConfig) {
        return ResponseEntity.ok(ruleConfigService.updateRuleConfig(id, ruleConfig));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRuleConfig(@PathVariable UUID id) {
        ruleConfigService.deleteRuleConfig(id);
        return ResponseEntity.ok().build();
    }
}
