package com.ecompulse.inventory.config;

import com.ecompulse.inventory.interceptor.ChaosInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ChaosInterceptor chaosInterceptor;

    public WebConfig(ChaosInterceptor chaosInterceptor) {
        this.chaosInterceptor = chaosInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(chaosInterceptor);
    }
}
