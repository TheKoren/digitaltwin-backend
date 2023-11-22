package com.koren.digitaltwin.analysis.enums;

import lombok.Getter;

@Getter
public enum MeasurementValueType {
    TEMPERATURE(11.11),
    HUMIDITY(10),
    PRESSURE(1),
    TVOC(10),
    SOUND(10),
    LIGHT(10),
    UV(10),
    ECO2(10);

    private final double threshold;

    MeasurementValueType(double threshold) {
        this.threshold = threshold;
    }

}

