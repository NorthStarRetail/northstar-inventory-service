package com.northstar.northstar.inventory.service.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("inventory")
public class AppConfiguration {
    private String msg;
    private String buildVersion;
    private String baseUrl;
}
