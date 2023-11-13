package com.koren.digitaltwin.models.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.koren.digitaltwin.models.message.Message;
import com.koren.digitaltwin.models.message.data.WifiNetwork;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonitorMessage extends Message {
    List<WifiNetwork> networksList = new ArrayList<>();

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
