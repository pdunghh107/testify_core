package com.zcomini.backend.testify.repository;

import com.zcomini.backend.testify.entity.ApiConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApiConfigRepository extends JpaRepository<ApiConfig, UUID> {
}
