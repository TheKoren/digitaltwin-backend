package com.koren.digitaltwin.models.message;

import com.koren.digitaltwin.models.message.data.SensorData;
import com.koren.digitaltwin.models.message.data.WifiData;
import com.koren.digitaltwin.utils.CastHelper;
import org.bson.types.ObjectId;
import org.springframework.boot.json.JacksonJsonParser;

import java.util.Map;

public class MessageFactory{
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

    Map<String, Object> parseJsonData(String data) {
        return new JacksonJsonParser().parseMap(data);
    }


}
