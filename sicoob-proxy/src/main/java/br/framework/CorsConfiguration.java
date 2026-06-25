package br.framework;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Libera todos os endpoints da API
                        .allowedOrigins("http://localhost:4200") // URL do seu Angular
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
                        .allowedHeaders("*") // Libera todos os headers (Authorization, Content-Type, etc)
                        .allowCredentials(true);
            }
        };
    }
}