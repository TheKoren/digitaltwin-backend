package com.koren.digitaltwin.models.message;

import com.koren.digitaltwin.models.message.data.WifiNetwork;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a monitor message that includes information about Wi-Fi networks.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonitorMessage extends Message {
    /** List of Wi-Fi networks associated with the monitor message. */
    List<WifiNetwork> networksList = new ArrayList<>();

    /**
     * Constructs a MonitorMessage instance with the provided ID, MAC address, and list of networks.
     *
     * @param objectId  Unique identifier for the message.
     * @param mac       MAC address associated with the message.
     * @param networks  List of Wi-Fi networks associated with the monitor message.
     */
    public MonitorMessage(ObjectId objectId, String mac, List<Map<String, Object>> networks) {
        super(objectId, mac);
        for (Map<String, Object> network : networks) {
            var newNetwork = new WifiNetwork(network.get("SSID").toString(),
                    Integer.parseInt(network.get("RSSI").toString()),
                    Integer.parseInt(network.get("Channel").toString()));
            networksList.add(newNetwork);
        }
    }
}
