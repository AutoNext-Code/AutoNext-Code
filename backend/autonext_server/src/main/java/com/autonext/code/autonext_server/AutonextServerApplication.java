package com.autonext.code.autonext_server;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableScheduling
@PropertySource("classpath:emailconfig.properties")
@EnableJpaRepositories("com.autonext.code.autonext_server.repositories")
public class AutonextServerApplication {

    public static void main(String[] args) {
        // Cargar dotenv ANTES de iniciar Spring Boot
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_USER", dotenv.get("DB_USER", "default_user"));
        System.setProperty("DB_PASS", dotenv.get("DB_PASS", "default_password"));
        System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET", "default_secret"));

        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Madrid"));

        SpringApplication.run(AutonextServerApplication.class, args);
    }
}
