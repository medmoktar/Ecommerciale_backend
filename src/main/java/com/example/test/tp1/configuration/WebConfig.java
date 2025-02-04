package com.example.test.tp1.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @SuppressWarnings("null")
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/Images/**")
                .addResourceLocations("file:C:/Users/medmo/Downloads/spring_gestion_locateur-main/spring_gestion_locateur-main/Images/");
    }
}
