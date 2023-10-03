package com.koren.digitaltwin.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private String configFilePath;

    public void setConfigFilePath(String configFilePath) {
        this.configFilePath = configFilePath;
    }

    @Bean
    public Config loadConfig() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File configFile = new ClassPathResource("config.json").getFile();

        return objectMapper.readValue(configFile, Config.class);
    }
}
