package com.koren.digitaltwin.models.message.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Represents data related to a Wi-Fi network, including SSID, RSSI, and channel.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WifiNetwork {

    /** Service Set Identifier (SSID) of the Wi-Fi network. */
    @Field
    String ssid;

    /** Received Signal Strength Indicator (RSSI) value for the Wi-Fi network. */
    @Field
    int rssi;

    /** Wi-Fi channel on which the network operates. */
    @Field
    int channel;
}
