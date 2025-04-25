package com.autonext.code.autonext_server.services;

import java.time.LocalDate;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.models.Strike;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.enums.Role;
import com.autonext.code.autonext_server.repositories.StrikeRepository;
import com.autonext.code.autonext_server.repositories.UserRepository;

@Service
public class UnpenaliceService {

    @Autowired
    private StrikeRepository strikeRepository;

    @Autowired
    private UserRepository userRepository;

    @Scheduled(fixedRate = 86400 * 1000)
    private void UnpenaliceCheck(){

        List<Strike> strikes = strikeRepository.findLatestActiveStrikesOfPenalizedUsers(Role.Penalized);
        LocalDate today = LocalDate.now();

        for (Strike strike : strikes) {
            LocalDate expirationDate = strike.getDate().plusDays(15);

            if (expirationDate.isBefore(today) || expirationDate.isEqual(today)) {
                User user = strike.getUser();
                
                
                user.setRole(Role.User);
                userRepository.save(user);

                
                user.getStrikes().stream()
                    .filter(Strike::isActive)
                    .forEach(s -> s.setActive(false));
            }
        } 
    }
    
}
