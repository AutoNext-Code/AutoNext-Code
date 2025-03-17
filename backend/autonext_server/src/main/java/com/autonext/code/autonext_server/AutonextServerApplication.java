package com.autonext.code.autonext_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.autonext.code.autonext_server.repositories")
public class AutonextServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutonextServerApplication.class, args);
	}

}
