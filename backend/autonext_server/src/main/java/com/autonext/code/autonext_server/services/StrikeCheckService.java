package com.autonext.code.autonext_server.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.models.Strike;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.repositories.StrikeRepository;

import jakarta.transaction.Transactional;

@Service
public class StrikeCheckService {
    
    @Autowired
    private StrikeRepository strikeRepository;

    @Scheduled(fixedRate = 86400 * 1000)
    private void StrikesBGService(){

        StrikeStatusCheck();       
    }

    @Transactional
    public void StrikeStatusCheck(){

        LocalDate now = LocalDate.now();

        List<Strike> strikes = strikeRepository.findActiveStrikes();



        Map<User, Optional<Strike>> lastStrike = strikes.stream()
            .collect(Collectors.groupingBy(
            Strike::getUser,
            Collectors.maxBy(Comparator.comparing(Strike::getDate))
        ));

        List<Strike> strikesToUpdate = new ArrayList<>();

        for (Optional<Strike> optionalStrike : lastStrike.values()) {
            optionalStrike.ifPresent(strike -> {
                long dias = ChronoUnit.DAYS.between(strike.getDate(), now);
                if (dias > 30) {
                    strike.setActive(false);
                    strikesToUpdate.add(strike);
                }
            });
        }



        strikeRepository.saveAll(strikesToUpdate);

    }

}
