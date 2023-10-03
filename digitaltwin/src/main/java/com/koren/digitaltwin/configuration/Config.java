package com.koren.digitaltwin.configuration;

import java.util.List;

public class Config {
    private List<String> esp32Devices;

    public List<String> getEsp32Devices() {
        return esp32Devices;
    }

    public void setEsp32Devices(List<String> esp32Devices) {
        this.esp32Devices = esp32Devices;
    }
}
