package com.koren.digitaltwin.models.message.data.wifi;

import com.koren.digitaltwin.utils.InvalidMeasurementValueException;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;
/**
 * Represents sensor data including temperature, humidity, pressure, TVOC, sound, light, UV, and ECO2 values.
 */
@Data
public class SensorData {

    /** Temperature value in degrees Celsius. */
    @Field
    public double temperatureValue;

    /** Humidity value as a percentage. */
    @Field
    public double humidityValue;

    /** Pressure value in hPa. */
    @Field
    public double pressure;

    /** eCO2 (Equivalent Carbon Dioxide) value in ppm. */
    @Field
    public long eco2;

    /** TVOC (Total Volatile Organic Compounds) value in ppb. */
    @Field
    public int tvocValue;

    /** Sound level value in decibels. */
    @Field
    public double sound;

    /** Light intensity value in lux. */
    @Field
    public double light;

    /** UV (Ultraviolet) index value. */
    @Field
    public int uv;

    /**
     * Constructs a SensorData instance from a map of sensor values.
     *
     * @param sensorValues The map containing sensor values.
     */
    public SensorData(Map<String, Object> sensorValues) {
        this.temperatureValue = validateNonZeroDouble(sensorValues, "temp") / 1000;
        this.humidityValue = validateNonZeroDouble(sensorValues, "hum") / 1000;
        this.pressure = validateNonZeroDouble(sensorValues, "press") / 10000;
        this.tvocValue = validateNonZeroInteger(sensorValues, "tvoc");
        this.sound = validateNonZeroDouble(sensorValues, "sound") / 100;
        this.light = validateNonZeroDouble(sensorValues, "light") / 10;
        this.uv = Integer.parseInt(sensorValues.get("uv").toString()); // UV not validated
        this.eco2 = validateNonZeroLong(sensorValues, "eco2");
    }

    private double validateNonZeroDouble(Map<String, Object> sensorValues, String key) {
        double value = Double.parseDouble(sensorValues.get(key).toString());
        if (value == 0) {
            throw new InvalidMeasurementValueException("Invalid " + key + " value: " + value);
        }
        return value;
    }

    private int validateNonZeroInteger(Map<String, Object> sensorValues, String key) {
        int value = Integer.parseInt(sensorValues.get(key).toString());
        if (value == 0) {
            throw new InvalidMeasurementValueException("Invalid " + key + " value: " + value);
        }
        return value;
    }

    private long validateNonZeroLong(Map<String, Object> sensorValues, String key) {
        long value = Long.parseLong(sensorValues.get(key).toString());
        if (value == 0) {
            throw new InvalidMeasurementValueException("Invalid " + key + " value: " + value);
        }
        return value;
    }


    /**
     * Constructs a SensorData instance with specified values.
     *
     * @param temperatureValue Temperature value.
     * @param humidityValue    Humidity value.
     * @param pressure         Pressure value.
     * @param tvocValue        TVOC value.
     * @param sound            Sound level value.
     * @param light            Light intensity value.
     * @param uv               UV index value.
     * @param eco2             eCO2 value.
     */
    public SensorData(double temperatureValue, double humidityValue, double pressure, int tvocValue, double sound, double light, int uv, long eco2) {
        this.temperatureValue = temperatureValue;
        this.humidityValue = humidityValue;
        this.pressure = pressure;
        this.tvocValue = tvocValue;
        this.sound = sound;
        this.light = light;
        this.uv = uv;
        this.eco2 = eco2;
    }

    /**
     * Default constructor initializing all values to zero.
     */
    public SensorData() {
        this.temperatureValue = 0;
        this.humidityValue = 0;
        this.pressure = 0;
        this.tvocValue = 0;
        this.sound = 0;
        this.light = 0;
        this.uv = 0;
        this.eco2 = 0;
    }

    /**
     * Returns a formatted string representation of the sensor data.
     *
     * @return String representation of the sensor data.
     */
    @Override
    public String toString() {
        return String.format("Temperature: %s, Humidity: %s, Pressure: %s, TVOC: %s. \n Sound: %s, Light: %s, UV: %s, ECO2: %s.",
                temperatureValue, humidityValue, pressure, tvocValue, sound, light, uv, eco2);
    }
}
