package me.elyor.memberservice.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

    private AppProperties properties;

    public WebMvcConfig(AppProperties properties) {
        this.properties = properties;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(properties
                        .cors().allowedOrigins().toArray(String[]::new))
                .allowedMethods(properties
                        .cors().allowedMethods().toArray(String[]::new))
                .allowedHeaders(CorsConfiguration.ALL);
        WebMvcConfigurer.super.addCorsMappings(registry);
    }

}
