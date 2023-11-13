package com.koren.digitaltwin.models.message.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WifiNetwork {
    @Field
    String ssid;
    @Field
    int rssi;
    @Field
    int channel;
}
