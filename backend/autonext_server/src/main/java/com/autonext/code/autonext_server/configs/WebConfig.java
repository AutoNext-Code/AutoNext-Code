package com.autonext.code.autonext_server.configs;

import java.io.File;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // Imprimir ruta absoluta de la carpeta 'uploads'
    String absolutePath = new File("uploads/").getAbsolutePath();
    System.out.println("🔧 Sirviendo imágenes desde: " + absolutePath);

    registry.addResourceHandler("/images/**")
            .addResourceLocations("file:/Uploads/")
            .setCachePeriod(3600);
  }
}
