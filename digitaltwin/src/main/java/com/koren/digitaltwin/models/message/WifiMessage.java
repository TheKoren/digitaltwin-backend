package com.koren.digitaltwin.models.message;

import com.koren.digitaltwin.models.message.data.SensorData;
import com.koren.digitaltwin.models.message.data.WifiData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Represents a Wi-Fi message with associated sensor and Wi-Fi data.
 */
@EqualsAndHashCode(callSuper = true)
@Document(collection="node-values")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WifiMessage extends Message{

    /** Sensor data associated with the Wi-Fi message. */
    @Field
    public SensorData sensorData;

    /** Wi-Fi data associated with the Wi-Fi message. */
    @Field
    public WifiData wifiData;

    /**
     * Constructs a WifiMessage instance with the provided ID, MAC address, sensor data, and Wi-Fi data.
     *
     * @param id         Unique identifier for the message.
     * @param mac        MAC address associated with the message.
     * @param sensorData Sensor data associated with the Wi-Fi message.
     * @param wifiData   Wi-Fi data associated with the Wi-Fi message.
     */
    WifiMessage(ObjectId id, String mac, SensorData sensorData, WifiData wifiData) {
        super(id, mac);
        this.timestamp = ZonedDateTime.now(ZoneId.systemDefault()).toInstant().plus(2, ChronoUnit.HOURS);
        this.sensorData = sensorData;
        this.wifiData = wifiData;
    }

}
