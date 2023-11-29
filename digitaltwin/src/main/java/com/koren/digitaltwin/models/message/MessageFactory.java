package com.koren.digitaltwin.models.message;

import com.koren.digitaltwin.models.message.data.wifi.SensorData;
import com.koren.digitaltwin.models.message.data.wifi.WifiData;
import com.koren.digitaltwin.utils.CastHelper;
import org.bson.types.ObjectId;
import org.springframework.boot.json.JacksonJsonParser;

import java.util.Map;

/**
 * Factory class for creating instances of messages based on JSON data.
 */
public class MessageFactory{
    /**
     * Creates a message based on the provided JSON data.
     *
     * @param data The JSON data representing the message.
     * @return An instance of a message (either MonitorMessage or WifiMessage).
     */
    public AbstractWifiMessage createMessage(String data) {
        try {
            var payload = parseJsonData(data);
            var header = CastHelper.castToMapStringObject(payload.get("header"));
            String deviceType = header.get("device") != null ? header.get("device").toString() : "";
            return switch (deviceType) {
                case "ESP8266" -> createMonitorWifiMessage(header, payload);
                case "ESP32" -> createNodeWifiMessage(header, payload);
                default -> throw new IllegalArgumentException("Unsupported device type " + deviceType);
            };
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error parsing JSON data", e);
        }
    }

    /**
     * Creates a MonitorWifiMessage instance based on the provided header and payload.
     *
     * @param header  The header information from the JSON data.
     * @param payload The payload information from the JSON data.
     * @return A new instance of MonitorWifiMessage.
     */
    private MonitorWifiMessage createMonitorWifiMessage(Map<String, Object> header, Map<String, Object> payload) {
        return new MonitorWifiMessage(
                new ObjectId(),
                header.get("mac").toString(),
                CastHelper.castToListMapStringObject(payload.get("networks"))
        );
    }

    /**
     * Creates a NodeWifiMessage instance based on the provided header and payload.
     *
     * @param header  The header information from the JSON data.
     * @param payload The payload information from the JSON data.
     * @return A new instance of NodeWifiMessage.
     */
    private NodeWifiMessage createNodeWifiMessage(Map<String, Object> header, Map<String, Object> payload) {
        return new NodeWifiMessage(
                new ObjectId(),
                header.get("mac").toString(),
                new SensorData(CastHelper.castToMapStringObject(payload.get("measurements"))),
                new WifiData(CastHelper.castToMapStringObject(payload.get("operational")))
        );
    }

    /**
     * Parses JSON data into a Map of key-value pairs.
     *
     * @param data The JSON data to be parsed.
     * @return A Map representing the parsed JSON data.
     */
    Map<String, Object> parseJsonData(String data) {
        return new JacksonJsonParser().parseMap(data);
    }
}
