package com.autonext.code.autonext_server.configs.seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.autonext.code.autonext_server.models.Config;
import com.autonext.code.autonext_server.repositories.ConfigRepository;


@Component
@Order(6)
public class ConfigSeeder  implements CommandLineRunner {

    private ConfigRepository configRepository;
    
    
    public ConfigSeeder(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }


    @Override
    public void run(String... args) {
        if (configRepository.count() == 0) {
            Config config = new Config(2, 30);

            configRepository.save(config);
        }
    }
}
