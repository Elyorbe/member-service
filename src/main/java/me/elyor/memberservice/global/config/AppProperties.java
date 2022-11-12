package me.elyor.memberservice.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "app")
public record AppProperties(
        Cors cors,
        Security security
) {}

record Cors(
        List<String> allowedOrigins,
        List<String> allowedMethods
) {}

record Security(
        String key
) {}
