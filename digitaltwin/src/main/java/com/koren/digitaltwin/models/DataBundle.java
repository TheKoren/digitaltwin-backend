package com.koren.digitaltwin.models;

import java.util.Map;

/**
 * Interface containing methods for the DataBundle model
 */
interface DataBundle {
    /**
     * Function to parse a Json
     * @param data valid JSON from a network enddevice
     * @return parsed Json in a Map
     */
    Map<String, Object> parseData(String data);

    /**
     * Function to get the MAC address from payload data
     * @return device mac address
     */
    String parseId();
}
