package com.koren.digitaltwin.models.message.data.wifi;

import com.koren.digitaltwin.models.message.data.wifi.enums.WifiMode;
import com.koren.digitaltwin.utils.CastHelper;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

/**
 * Represents data related to Wi-Fi, including RSSI, TxPower, channel, mode, sensorRead, sensorExec, and addressList.
 */
@Data
public class WifiData {
    /** Received Signal Strength Indicator (RSSI) value. */
    @Field
    public int rssi;

    /** Transmit Power (TxPower) value. */
    @Field
    public int txPower;

    /** Wi-Fi channel value. */
    @Field
    public int channel;

    /** Wi-Fi mode (UNKNOWN, STATION, AP_STA). */
    @Field
    public WifiMode mode = WifiMode.UNKNOWN;

    /** Timestamp of sensor reading. */
    @Field
    public long sensorRead;

    /** Timestamp of sensor execution. */
    @Field
    public long sensorExec;

    /** List of addresses in AP_STA mode. */
    @Field
    public List<String> addressList;

    /**
     * Constructs a WifiData instance from a map of Wi-Fi values.
     *
     * @param wifiValues The map containing Wi-Fi values.
     */
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
        this.sensorExec = Long.parseLong(wifiValues.get("sensorExec").toString());
        this.sensorRead = Long.parseLong(wifiValues.get("sensorRead").toString());
    }

    /**
     * Constructs a WifiData instance with specified values.
     *
     * @param rssi       RSSI value.
     * @param channel    Wi-Fi channel value.
     * @param txPower    TxPower value.
     * @param addressList List of addresses in AP_STA mode.
     * @param mode       Wi-Fi mode.
     * @param sensorExec Timestamp of sensor execution.
     * @param sensorRead Timestamp of sensor reading.
     */
    public WifiData(int rssi, int channel, int txPower, List<String> addressList, WifiMode mode, long sensorExec, long sensorRead) {
        this.rssi = rssi;
        this.channel = channel;
        this.txPower = txPower;
        this.addressList = addressList;
        this.mode = mode;
        this.sensorRead = sensorRead;
        this.sensorExec = sensorExec;
    }

    /**
     * Default constructor initializing all values to zero or null.
     */
    public WifiData() {
        this.rssi = 0;
        this.channel = 0;
        this.txPower = 0;
        this.addressList = null;
        this.sensorRead = 0;
        this.sensorExec = 0;
    }

    /**
     * Returns a formatted string representation of the Wi-Fi data.
     *
     * @return String representation of the Wi-Fi data.
     */
    @Override
    public String toString(){
        return String.format("rssi: %s, txPower: %s, channel: %s, mode: %s, SensorRead: %s, SensorExec: %s", rssi, txPower, channel, mode, sensorRead, sensorExec);
    }
}
