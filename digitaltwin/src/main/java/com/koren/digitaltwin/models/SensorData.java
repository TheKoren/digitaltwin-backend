package com.koren.digitaltwin.models;

import java.util.Map;

public class SensorData {
    public final int temperateValue;
    public final int humidityValue;
    public final int pressure;
    public final int tvocValue;

    public SensorData(Map<String, Object> payload) {
        Map<String, Object> sensorValues = (Map<String, Object>) payload.get("sensorValues");
        this.temperateValue = Integer.parseInt(sensorValues.get("temp").toString());
        this.humidityValue = Integer.parseInt(sensorValues.get("hum").toString());
        this.pressure = Integer.parseInt(sensorValues.get("press").toString());
        this.tvocValue = Integer.parseInt(sensorValues.get("gas").toString());
    }

    @Override
    public String toString() {
        return String.format("Temperature: %s, Humidity: %s, Pressure: %s, TVOC: %s. \n", temperateValue, humidityValue, pressure, tvocValue);
    }
}
