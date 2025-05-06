package com.autonext.code.autonext_server.configs.seeders;

import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.enums.JobPosition;
import com.autonext.code.autonext_server.models.enums.Role;
import com.autonext.code.autonext_server.repositories.UserRepository;
import com.autonext.code.autonext_server.repositories.WorkCenterRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final WorkCenterRepository workCenterRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserSeeder(UserRepository userRepository, WorkCenterRepository workCenterRepository) {
        this.userRepository = userRepository;
        this.workCenterRepository = workCenterRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User admin = new User("admin@example.com", "Admin", "User", passwordEncoder.encode("admin123"), true);
            admin.setJobPosition(JobPosition.Branch_Head);
            admin.setRole(Role.Admin);
            workCenterRepository.findById(2).ifPresent(admin::setWorkCenter);

            User user = new User("user@example.com", "User", "User", passwordEncoder.encode("user123"), true);
            user.setJobPosition(JobPosition.Developer);
            user.setRole(Role.User);

            User prueba = new User("prueba@example.com", "Prueba", "Prueba", passwordEncoder.encode("prueba123"), true);
            prueba.setJobPosition(JobPosition.Intern);
            prueba.setRole(Role.User);

            User banned = new User("banned@example.com", "Banned", "Banned", passwordEncoder.encode("banned123"), true);
            banned.setJobPosition(JobPosition.Product_Owner);
            banned.setRole(Role.Banned);

            User penalized = new User("penalized@example.com", "Penalized", "Penalized", passwordEncoder.encode("penalized123"), true);
            penalized.setJobPosition(JobPosition.Scrum_Master);
            penalized.setRole(Role.Penalized);

            userRepository.saveAll(List.of(admin, user, prueba, banned, penalized));
            System.out.println("Usuarios creados con éxito");
        } else {
            System.out.println("La base de datos ya tiene usuarios. Seeding skipped.");
        }
    }
}