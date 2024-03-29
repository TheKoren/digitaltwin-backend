package com.koren.digitaltwin.analysis;

import com.koren.digitaltwin.analysis.enums.MeasurementValueType;
import com.koren.digitaltwin.models.message.NodeWifiMessage;
import com.koren.digitaltwin.models.notification.DropNotification;
import com.koren.digitaltwin.models.notification.NotificationType;
import com.koren.digitaltwin.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Component responsible for analyzing drops in various measurement values.
 */
@Component
public class DropAnalyzer {
    @Autowired
    private NotificationService notificationService;

    // Internal cache to store the latest timestamp for each measurement value type and device
    Map<String, Map<MeasurementValueType, Long>> internalCache = new HashMap<>();

    /**
     * Detect drops in all measurement value types for a given list of WifiMessages.
     *
     * @param messageList List of WifiMessages for analysis.
     */
    public void detectDrops(List<NodeWifiMessage> messageList) {
        if (!internalCache.containsKey(messageList.get(0).getMac())) {
            initInternalCache(messageList.get(0).getMac());
        }
        for (MeasurementValueType valueType : MeasurementValueType.values()) {
            detectMeasurementDrop(messageList, valueType);
        }
    }

    /**
     * Initialize the internal cache for a specific device.
     *
     * @param key Device MAC address.
     */
    private void initInternalCache(String key) {
        internalCache.put(key, new HashMap<>());
        var innerMap = internalCache.get(key);
        innerMap.put(MeasurementValueType.TEMPERATURE, 0L);
        innerMap.put(MeasurementValueType.HUMIDITY, 0L);
        innerMap.put(MeasurementValueType.ECO2, 0L);
        innerMap.put(MeasurementValueType.PRESSURE, 0L);
        innerMap.put(MeasurementValueType.TVOC, 0L);
        innerMap.put(MeasurementValueType.UV, 0L);
        innerMap.put(MeasurementValueType.SOUND, 0L);
        innerMap.put(MeasurementValueType.LIGHT, 0L);
    }

    /**
     * Detect drops in a specific measurement value type for a given list of WifiMessages.
     *
     * @param messageList          List of WifiMessages for analysis.
     * @param measurementValueType Measurement value type for analysis.
     */
    private void detectMeasurementDrop(List<NodeWifiMessage> messageList, MeasurementValueType measurementValueType) {
        if (messageList.size() < 2) {
            return;
        }
        String mac = messageList.get(0).getMac();
        for (int i = messageList.size() - 1; i >= 1; i--) {
            NodeWifiMessage currentMessage = messageList.get(i - 1);
            if (currentMessage.getTimestamp().toEpochMilli() < internalCache.get(mac).get(measurementValueType)) {
                continue;
            }
            NodeWifiMessage previousMessage = messageList.get(i);

            double valueDifference = getValueDifference(currentMessage, previousMessage, measurementValueType);
            double percentageDifference = (valueDifference / getValue(currentMessage, measurementValueType)) * 100;

            if (percentageDifference > measurementValueType.getThreshold()) {
                notificationService.saveNotification(new DropNotification(NotificationType.ANALYSIS, "Signifact measurement drop detected. ", currentMessage, measurementValueType, previousMessage.getFromType(measurementValueType), currentMessage.getFromType(measurementValueType)));
                long timestamp = currentMessage.getTimestamp().toEpochMilli();
                internalCache.get(mac).put(measurementValueType, timestamp);
            }
        }
    }

    /**
     * Get the difference in a specific measurement value between two WifiMessages.
     *
     * @param currentMessage  The current WifiMessage.
     * @param previousMessage The previous WifiMessage.
     * @param valueType       Measurement value type.
     * @return The absolute difference in the measurement value.
     */
    private double getValueDifference(NodeWifiMessage currentMessage, NodeWifiMessage previousMessage, MeasurementValueType valueType) {
        return switch (valueType) {
            case TEMPERATURE ->
                    Math.abs(currentMessage.getSensorData().getTemperatureValue() - previousMessage.getSensorData().getTemperatureValue());
            case TVOC ->
                    Math.abs(currentMessage.getSensorData().getTvocValue() - previousMessage.getSensorData().getTvocValue());
            case HUMIDITY ->
                    Math.abs(currentMessage.getSensorData().getHumidityValue() - previousMessage.getSensorData().getHumidityValue());
            case ECO2 -> Math.abs(currentMessage.getSensorData().getEco2() - previousMessage.getSensorData().getEco2());
            case PRESSURE ->
                    Math.abs(currentMessage.getSensorData().getPressure() - previousMessage.getSensorData().getPressure());
            case LIGHT ->
                    Math.abs(currentMessage.getSensorData().getLight() - previousMessage.getSensorData().getLight());
            case UV -> Math.abs(currentMessage.getSensorData().getUv() - previousMessage.getSensorData().getUv());
            case SOUND ->
                    Math.abs(currentMessage.getSensorData().getSound() - previousMessage.getSensorData().getSound());
            default -> throw new IllegalArgumentException("Unsupported measurement value type");
        };
    }


    /**
     * Get the value of a specific measurement type from a WifiMessage.
     *
     * @param message   The WifiMessage.
     * @param valueType Measurement value type.
     * @return The value of the measurement type.
     */
    private double getValue(NodeWifiMessage message, MeasurementValueType valueType) {
        return switch (valueType) {
            case TEMPERATURE -> message.getSensorData().getTemperatureValue();
            case TVOC -> message.getSensorData().getTvocValue();
            case HUMIDITY -> message.getSensorData().getHumidityValue();
            case ECO2 -> message.getSensorData().getEco2();
            case PRESSURE -> message.getSensorData().getPressure();
            case LIGHT -> message.getSensorData().getLight();
            case UV -> message.getSensorData().getUv();
            case SOUND -> message.getSensorData().getSound();
            default -> throw new IllegalArgumentException("Unsupported measurement value type");
        };
    }
}
