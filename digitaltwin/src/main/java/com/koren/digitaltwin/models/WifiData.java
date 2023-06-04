package com.koren.digitaltwin.models;

import com.koren.digitaltwin.models.enums.WifiMode;

import java.util.List;
import java.util.Map;

public class WifiData {
    public final int rssi;
    public final int txPower;
    public final int channel;
    public WifiMode mode = WifiMode.UNKNOWN;
    public List<String> addressList;

    public WifiData(Map<String, Object> payload) {
        Map<String, Object> wifiValues = (Map<String, Object>) payload.get("wifiValues");
        this.rssi = Integer.parseInt(wifiValues.get("rssi").toString());
        this.channel = Integer.parseInt(wifiValues.get("channel").toString());
        this.txPower = Integer.parseInt(wifiValues.get("TxPower").toString());
        var mode = wifiValues.get("mode").toString();
        switch (mode) {
            case "WIFI_STA" -> this.mode = WifiMode.STATION;
            case "WIFI_AP_STA" -> {
                this.mode = WifiMode.AP_STA;
                addressList = (List<String>) wifiValues.get("addressList");
            }
            default -> {
            }
        }
    }

    @Override
    public String toString(){
        return String.format("rssi: %s, txPower: %s, channel: %s, mode: %s", rssi, txPower, channel, mode);
    }
}
