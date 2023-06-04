package com.koren.digitaltwin.models;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Arrays;
import java.util.Map;

public class DataBundleImpl implements DataBundle {
    @Id
    public final int id;
    public final String mac;
    public final Instant timeStamp;
    public SensorData sensorData;
    public WifiData wifiData;
    public Map<String, Object> payload;

    public DataBundleImpl (String data, int id) {
        this.id = id;
        this.timeStamp = Instant.now();
        this.payload = parseData(data);
        this.mac = parseId();
        this.sensorData = new SensorData(payload);
        this.wifiData = new WifiData(payload);
    }

    @Override
    public Map<String, Object> parseData(String data) {
        return new JacksonJsonParser().parseMap(data);
    }

    @Override
    public String parseId() {
        return Arrays.stream(this.payload.get("deviceId").toString().split("-")).toList().get(1);
    }
}
