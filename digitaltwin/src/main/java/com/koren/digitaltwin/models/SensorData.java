package com.koren.digitaltwin.models;

public class SensorData {
    public int nodeId;
    public int temperateValue;
    public int humidityValue;
    public int tvocValue;
    public WifiSignal wifiValues;

    public SensorData() {

    }

    public SensorData(int nodeId, int temperateValue, int humidityValue, int tvocValue, WifiSignal wifiValues) {
        this.nodeId = nodeId;
        this.temperateValue = temperateValue;
        this.humidityValue = humidityValue;
        this.tvocValue = tvocValue;
        this.wifiValues = wifiValues;
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Temperature: %s, Humidity: %s, TVOC: %s, Wifi: %s\n", nodeId, temperateValue, humidityValue, tvocValue, wifiValues.toString());
    }
}
