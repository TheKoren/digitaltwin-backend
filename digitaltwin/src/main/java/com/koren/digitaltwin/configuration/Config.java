package com.koren.digitaltwin.configuration;

import java.util.List;

/**
 * Represents the application configuration, including a list of ESP32 devices.
 */
public class Config {

    /** The list of ESP32 devices configured in the application. */
    private List<String> esp32Devices;

    /**
     * Gets the list of ESP32 devices.
     *
     * @return The list of ESP32 devices.
     */
    public List<String> getEsp32Devices() {
        return esp32Devices;
    }

    /**
     * Sets the list of ESP32 devices.
     *
     * @param esp32Devices The list of ESP32 devices to set.
     */
    public void setEsp32Devices(List<String> esp32Devices) {
        this.esp32Devices = esp32Devices;
    }
}
