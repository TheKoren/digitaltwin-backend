package com.koren.digitaltwin.models.message;

import com.koren.digitaltwin.models.message.data.SensorData;
import com.koren.digitaltwin.models.message.data.WifiData;
import com.koren.digitaltwin.utils.CastHelper;
import org.bson.types.ObjectId;
import org.springframework.boot.json.JacksonJsonParser;

import java.util.Map;

public class WifiMessageFactory{
    public WifiMessage createMessage(String data) {
        var payload = parseJsonData(data);
        return new WifiMessage(
                new ObjectId(),
                CastHelper.castToMapStringObject(payload.get("header")).get("mac").toString(),
                new SensorData(CastHelper.castToMapStringObject(payload.get("measurements"))),
                new WifiData(CastHelper.castToMapStringObject(payload.get("operational")))
        );
    }

    Map<String, Object> parseJsonData(String data) {
        return new JacksonJsonParser().parseMap(data);
    }


}
