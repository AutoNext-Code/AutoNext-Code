package com.autonext.code.autonext_server.configs;
 
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.web.servlet.config.annotation.CorsRegistry;
 import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 
 @Configuration
 public class CorsConfig {
 
   @Value("${cors.allowedOrigins}")
   private String allowedOrigins;
 
   @Bean
   public WebMvcConfigurer corsConfigurer() {
     return new WebMvcConfigurer() {
       @SuppressWarnings("null")
       @Override
       public void addCorsMappings(CorsRegistry registry) {
         registry.addMapping("/**")
             .allowedOrigins(allowedOrigins.split(","))
             .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
             .allowedHeaders("*")
             .allowCredentials(true);
       }
     };
   }
 }