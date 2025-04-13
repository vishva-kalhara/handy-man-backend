package io.github.vishvakalhara.handymanbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig  {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:3000") // frontend URL
                        .allowedMethods("GET", "POST", "PATCH", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true); // Only if using cookies or auth headers
            }
        };
    }
}
