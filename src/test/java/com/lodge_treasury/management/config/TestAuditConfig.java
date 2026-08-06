package com.lodge_treasury.management.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@TestConfiguration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class TestAuditConfig {
    @Bean
    @Primary
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("test-user");
    }
}
