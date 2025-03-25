package com.autonext.code.autonext_server.configs;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class EnvConfig {

    private final Dotenv dotenv = Dotenv.configure().directory("src/main/resources").load();

    @Bean
    public String jwtSecret() {
        return dotenv.get("JWT_SECRET", "default_secret_if_not_found");
    }

    @Bean
    public String dbUser() {
        return dotenv.get("DB_USER", "default_user");
    }

    @Bean
    public String dbPassword() {
        return dotenv.get("DB_PASSWORD", "default_password");
    }
}
