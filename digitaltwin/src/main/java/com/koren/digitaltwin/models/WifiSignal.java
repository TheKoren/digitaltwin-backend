package com.koren.digitaltwin.models;

public class WifiSignal {
    public int rssi;
    public int txPower;
    public int channel;

    public WifiSignal(){
    }

    public WifiSignal(int rssi, int txPower, int channel) {
        this.rssi = rssi;
        this.txPower = txPower;
        this.channel = channel;
    }

    @Override
    public String toString(){
        return String.format("rssi: %s, txPower: %s, channel: %s", rssi, txPower, channel);
    }



}
