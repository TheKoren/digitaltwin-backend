package com.koren.digitaltwin.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

/**
 * Configuration class responsible for loading application configuration from a JSON file.
 */
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    /** The file path of the configuration file. */
    private String configFilePath;

    /**
     * Sets the file path of the configuration file.
     *
     * @param configFilePath The file path of the configuration file.
     */
    public void setConfigFilePath(String configFilePath) {
        this.configFilePath = configFilePath;
    }

    /**
     * Bean method to load the application configuration from the specified JSON file.
     *
     * @return An instance of the Config class representing the loaded configuration.
     * @throws IOException If an I/O error occurs during the configuration loading process.
     */
    @Bean
    public Config loadConfig() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File configFile = new ClassPathResource("config.json").getFile();

        return objectMapper.readValue(configFile, Config.class);
    }
}
