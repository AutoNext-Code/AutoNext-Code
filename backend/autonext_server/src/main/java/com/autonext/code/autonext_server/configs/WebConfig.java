package com.autonext.code.autonext_server.configs;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/*images/**")
        .addResourceLocations("file:uploads/")
        .setCachePeriod(3600); // Cache for 1 hour
  }
}
