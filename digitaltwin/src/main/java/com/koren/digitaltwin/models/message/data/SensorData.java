package com.koren.digitaltwin.models.message.data;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
public class SensorData {
    @Field
    public double temperateValue;
    @Field
    public double humidityValue;
    @Field
    public double pressure;
    @Field
    public long eco2;
    @Field
    public int tvocValue;
    @Field
    public double sound;
    @Field
    public double light;
    @Field
    public int uv;


    public SensorData(Map<String, Object> sensorValues) {
        this.temperateValue = Double.parseDouble(sensorValues.get("temp").toString()) / 1000;
        this.humidityValue = Double.parseDouble(sensorValues.get("hum").toString()) / 1000;
        this.pressure = Double.parseDouble(sensorValues.get("press").toString()) / 10000;
        this.tvocValue = Integer.parseInt(sensorValues.get("tvoc").toString());
        this.sound = Double.parseDouble(sensorValues.get("sound").toString()) / 100;
        this.light = Double.parseDouble(sensorValues.get("light").toString()) / 10;
        this.uv = Integer.parseInt(sensorValues.get("uv").toString());
        this.eco2 = Long.parseLong(sensorValues.get("eco2").toString());
    }

    public SensorData(double temperateValue, double humidityValue, double pressure, int tvocValue, double sound, double light, int uv, long eco2) {
        this.temperateValue = temperateValue;
        this.humidityValue = humidityValue;
        this.pressure = pressure;
        this.tvocValue = tvocValue;
        this.sound = sound;
        this.light = light;
        this.uv = uv;
        this.eco2 = eco2;
    }

    public SensorData() {
        this.temperateValue = 0;
        this.humidityValue = 0;
        this.pressure = 0;
        this.tvocValue = 0;
        this.sound = 0;
        this.light = 0;
        this.uv = 0;
        this.eco2 = 0;
    }



    @Override
    public String toString() {
        return String.format("Temperature: %s, Humidity: %s, Pressure: %s, TVOC: %s. \n Sound: %s, Light: %s, UV: %s, ECO2: %s.",
                temperateValue, humidityValue, pressure, tvocValue, sound, light, uv, eco2);
    }
}
