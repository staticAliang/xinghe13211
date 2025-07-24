package com.fengshen.core.config;

import org.springframework.web.filter.*;
import org.springframework.web.cors.*;
import org.springframework.context.annotation.*;

@Configuration
public class CorsConfig
{
    private CorsConfiguration buildConfig() {
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }
    
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", this.buildConfig());
        return new CorsFilter((CorsConfigurationSource)source);
    }
}
