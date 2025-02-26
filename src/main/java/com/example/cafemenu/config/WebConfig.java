package com.example.cafemenu.config;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Configure CORS settings
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")  // Your frontend URL   //http://www.ilkerkalecikmenu.shop/
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allowed HTTP methods
                .allowedHeaders("*")  // Allowed headers
                .allowCredentials(true);  // Allow credentials if needed
    }

    // Add resource handlers for static files (like images, etc.)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Specify the 'uploads' folder to serve static files
        registry.addResourceHandler("/uploads/**")
        .addResourceLocations("file:./uploads/");   //file:/var/www/uploads/
    }
   
}
