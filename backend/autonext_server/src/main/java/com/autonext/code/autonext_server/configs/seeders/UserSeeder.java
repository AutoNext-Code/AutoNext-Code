package com.autonext.code.autonext_server.configs.seeders;

import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.Role;
import com.autonext.code.autonext_server.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User admin = new User("admin@example.com", "Admin", "User", passwordEncoder.encode("admin123"), true);
            admin.setJobPosition("Web administrator");
            admin.setRole(Role.Admin);

            User user = new User("user@example.com", "User", "User", passwordEncoder.encode("user123"), true);
            user.setJobPosition("Developer");
            user.setRole(Role.User);

            userRepository.saveAll(List.of(admin, user));
            System.out.println("Usuarios creados con éxito");
        } else {
            System.out.println("La base de datos ya tiene usuarios. Seeding skipped.");
        }
    }
}