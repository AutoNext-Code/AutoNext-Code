package com.autonext.code.autonext_server.configs;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.autonext.code.autonext_server.models.Role;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.repositories.UserRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  public DatabaseSeeder(UserRepository userRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = new BCryptPasswordEncoder();
  }

  @Override
  public void run(String... args) {
    if (userRepository.count() == 0) { // Solo insertar si la BD está vacía
      // Crea usuarios
      User admin = new User("admin@example.com", "Admin", "User", passwordEncoder.encode("admin123"));
      admin.setRol(Role.Admin);

      User user = new User("user@example.com", "User", "User", passwordEncoder.encode("user123"));
      user.setRol(Role.User);

      // Guarda en la BD
      userRepository.saveAll(List.of(admin, user));

      System.out.println("Database seeded successfully! (Admin & User created)");
    } else {
      System.out.println("Database already has users. Seeding skipped.");
    }
  }
}