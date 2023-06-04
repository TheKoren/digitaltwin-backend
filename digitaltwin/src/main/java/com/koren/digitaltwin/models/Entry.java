package com.koren.digitaltwin.models;

import com.koren.digitaltwin.models.enums.WifiMode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Document(collection="data")
public class Entry implements Serializable {
    @Id
    private Integer id;
    private String mac;
    private Instant timeStamp;
    private Integer temperateValue;
    private Integer humidityValue;
    private Integer pressure;
    private Integer tvocValue;
    private Integer rssi;
    private Integer txPower;
    private Integer channel;
    private WifiMode mode = WifiMode.UNKNOWN;
    private List<String> addressList;

    public Entry(Integer id, String mac, Instant timeStamp, Integer temperateValue, Integer humidityValue, Integer pressure,
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
