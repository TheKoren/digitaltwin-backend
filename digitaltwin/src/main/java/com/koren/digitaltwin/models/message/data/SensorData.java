package com.koren.digitaltwin.models.message.data;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
public class SensorData {
    @Field
    public int temperateValue;
    @Field
    public int humidityValue;
    @Field
    public int pressure;
    @Field
    public int tvocValue;


    public SensorData(Map<String, Object> sensorValues) {
        this.temperateValue = Integer.parseInt(sensorValues.get("temp").toString());
        this.humidityValue = Integer.parseInt(sensorValues.get("hum").toString());
        this.pressure = Integer.parseInt(sensorValues.get("press").toString());
        this.tvocValue = Integer.parseInt(sensorValues.get("gas").toString());
    }

    public SensorData(int temperateValue, int humidityValue, int pressure, int tvocValue) {
        this.temperateValue = temperateValue;
        this.humidityValue = humidityValue;
        this.pressure = pressure;
        this.tvocValue = tvocValue;
    }

    public SensorData() {
        this.temperateValue = 0;
        this.humidityValue = 0;
        this.pressure = 0;
        this.tvocValue = 0;
    }



    @Override
    public String toString() {
        return String.format("Temperature: %s, Humidity: %s, Pressure: %s, TVOC: %s. \n", temperateValue, humidityValue, pressure, tvocValue);
    }
}
