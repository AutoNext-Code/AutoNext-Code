package com.autonext.code.autonext_server.configs.seeders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.autonext.code.autonext_server.models.Strike;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.enums.StrikeReason;
import com.autonext.code.autonext_server.repositories.StrikeRepository;
import com.autonext.code.autonext_server.repositories.UserRepository;

@Component
public class StrikeSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final StrikeRepository strikeRepository;

    public StrikeSeeder(UserRepository userRepository, StrikeRepository strikeRepository) {
        this.userRepository = userRepository;
        this.strikeRepository = strikeRepository;
    }

    @Override
    public void run(String... args) {
        Optional<User> pruebaOpt = userRepository.findByEmail("prueba@example.com");

        if (pruebaOpt.isPresent()) {
            User prueba = pruebaOpt.get();

            Strike strike1 = new Strike(LocalDate.now(), LocalTime.now(), StrikeReason.NOTCONFIRMED, prueba);
            Strike strike2 = new Strike(LocalDate.now().minusDays(1), LocalTime.now(), StrikeReason.NOTCONFIRMED,
                    prueba);

            prueba.setStrikes(new ArrayList<>(List.of(strike1, strike2)));
            strikeRepository.saveAll(List.of(strike1, strike2));
        }
    }
}
