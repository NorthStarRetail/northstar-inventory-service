package com.northstar.northstar.inventory.service;

import com.northstar.northstar.inventory.service.configuration.AppConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = AppConfiguration.class)
public class NorthstarInventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NorthstarInventoryServiceApplication.class, args);
    }
}
