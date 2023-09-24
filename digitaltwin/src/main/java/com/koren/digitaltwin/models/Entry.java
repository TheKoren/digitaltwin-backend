package com.koren.digitaltwin.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import com.koren.digitaltwin.models.enums.WifiMode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Document(collection="node-values")
@Data
public class Entry implements Serializable {
    @Id
    private ObjectId id;
    private final String mac;
    public final Instant timeStamp;
    private final Integer temperateValue;
    private final Integer humidityValue;
    private final Integer pressure;
    private final Integer tvocValue;
    private final Integer rssi;
    private final Integer txPower;
    private final Integer channel;
    private WifiMode mode = WifiMode.UNKNOWN;
    private final List<String> addressList;

    public Entry(ObjectId id, String mac, Instant timeStamp, Integer temperateValue, Integer humidityValue, Integer pressure,
                 Integer tvocValue, Integer rssi, Integer txPower, Integer channel, WifiMode mode, List<String> addressList) {
        this.id = id;
        this.mac = mac;
        this.timeStamp = timeStamp;
        this.temperateValue = temperateValue;
        this.humidityValue = humidityValue;
        this.pressure = pressure;
        this.tvocValue = tvocValue;
        this.rssi = rssi;
        this.txPower = txPower;
        this.channel = channel;
        this.mode = mode;
        this.addressList = addressList;
    }

    @Override
    public String toString(){
        return String.format("Id: %d, MAC: %s, Timestamp: %s, Temperature: %s, Humidity: %s, Pressure: %s, TVOC: %s, rssi: %s, txPower: %s, channel: %s, mode: %s, addressList: %s\n.",
                id, mac, timeStamp, temperateValue, humidityValue, pressure, tvocValue, rssi, txPower, channel, mode, addressList);
    }
}
