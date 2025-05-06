package com.nancologist.car360;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //  Allow all endpoints
                .allowedOrigins("http://localhost:4200") //  Allow requests from your Angular app
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") //  Allowed HTTP methods
                .allowedHeaders("Content-Type", "Authorization") //  Allowed headers
                .allowCredentials(true) //  Allow sending cookies (if needed)
                .maxAge(3600); //  Cache preflight response for 1 hour
    }
}
