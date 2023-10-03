package com.koren.digitaltwin.models.message;

import com.koren.digitaltwin.models.message.data.SensorData;
import com.koren.digitaltwin.models.message.data.WifiData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document(collection="node-values")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WifiMessage {

    @Id
    private ObjectId id;
    public String mac;
    public Instant timestamp;
    @Field
    public SensorData sensorData;
    @Field
    public WifiData wifiData;

    WifiMessage(ObjectId id, String mac, SensorData sensorData, WifiData wifiData) {
        this.id = id;
        this.mac = mac;
        this.timestamp = Instant.now();
        this.sensorData = sensorData;
        this.wifiData = wifiData;
    }

}