package com.autonext.code.autonext_server;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de mi Proyecto")
                        .version("1.0")
                        .description("Documentación de la API de mi aplicación Spring Boot"));
    }
}
