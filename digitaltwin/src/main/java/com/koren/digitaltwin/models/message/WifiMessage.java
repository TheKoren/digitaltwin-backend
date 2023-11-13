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

@EqualsAndHashCode(callSuper = true)
@Document(collection="node-values")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WifiMessage extends Message{
    @Field
    public SensorData sensorData;
    @Field
    public WifiData wifiData;

    WifiMessage(ObjectId id, String mac, SensorData sensorData, WifiData wifiData) {
        super(id, mac);
        this.timestamp = ZonedDateTime.now(ZoneId.systemDefault()).toInstant().plus(2, ChronoUnit.HOURS);
        this.sensorData = sensorData;
        this.wifiData = wifiData;
    }

}
