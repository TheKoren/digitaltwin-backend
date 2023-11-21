package com.koren.digitaltwin.models.message;

import com.koren.digitaltwin.models.message.data.SensorData;
import com.koren.digitaltwin.models.message.data.WifiData;
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
    public Message createMessage(String data) {
        var payload = parseJsonData(data);
        var header = CastHelper.castToMapStringObject(payload.get("header"));
        if ("ESP8266".equals(header.get("device"))) {
            return new MonitorMessage(
                new ObjectId(),
                header.get("mac").toString(),
                CastHelper.castToListMapStringObject(payload.get("networks"))
            );
        } else if ("ESP32".equals(header.get("device"))) {
            return new WifiMessage(
                    new ObjectId(),
                    header.get("mac").toString(),
                    new SensorData(CastHelper.castToMapStringObject(payload.get("measurements"))),
                    new WifiData(CastHelper.castToMapStringObject(payload.get("operational")))
            );
        }
        System.out.println("nah mate");
        return null;
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
