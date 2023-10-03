package com.koren.digitaltwin.models.message.data;

import com.koren.digitaltwin.models.enums.WifiMode;
import com.koren.digitaltwin.utils.CastHelper;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

@Data
public class WifiData {
    @Field
    public int rssi;
    @Field
    public int txPower;
    @Field
    public int channel;
    @Field
    public WifiMode mode = WifiMode.UNKNOWN;
    @Field
    public List<String> addressList;

    public WifiData(Map<String, Object> wifiValues) {
        this.rssi = Integer.parseInt(wifiValues.get("rssi").toString());
        this.channel = Integer.parseInt(wifiValues.get("channel").toString());
        this.txPower = Integer.parseInt(wifiValues.get("TxPower").toString());
        var mode = wifiValues.get("mode").toString();
        switch (mode) {
            case "WIFI_STA" -> this.mode = WifiMode.STATION;
            case "WIFI_AP_STA" -> {
                this.mode = WifiMode.AP_STA;
                addressList = wifiValues.get("addressList") != null ? CastHelper.castToListString(wifiValues.get("addressList")) : null;
            }
            default -> {
            }
        }
    }

    public WifiData(int rssi, int channel, int txPower, List<String> addressList, WifiMode mode) {
        this.rssi = rssi;
        this.channel = channel;
        this.txPower = txPower;
        this.addressList = addressList;
        this.mode = mode;
    }

    public WifiData() {
        this.rssi = 0;
        this.channel = 0;
        this.txPower = 0;
        this.addressList = null;
    }

    @Override
    public String toString(){
        return String.format("rssi: %s, txPower: %s, channel: %s, mode: %s", rssi, txPower, channel, mode);
    }
}
