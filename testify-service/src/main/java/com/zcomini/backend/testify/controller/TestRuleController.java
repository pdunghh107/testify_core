package com.zcomini.backend.testify.controller;

import com.zcomini.backend.shared.exception.ResourceNotFoundException;
import com.zcomini.backend.testify.entity.TestRule;
import com.zcomini.backend.testify.repository.TestRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rules")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TestRuleController {

    private final TestRuleRepository testRuleRepository;

    @PostMapping
    public ResponseEntity<TestRule> createRule(@RequestBody TestRule rule) {
        return ResponseEntity.ok(testRuleRepository.save(rule));
    }

    @GetMapping
    public ResponseEntity<List<TestRule>> getAllRules() {
        return ResponseEntity.ok(testRuleRepository.findAll());
    }

    @GetMapping("/{ruleCode}")
    public ResponseEntity<TestRule> getRuleByCode(@PathVariable String ruleCode) {
        TestRule rule = testRuleRepository.findByRuleCode(ruleCode)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy rule code: " + ruleCode));
        return ResponseEntity.ok(rule);
    }
}
