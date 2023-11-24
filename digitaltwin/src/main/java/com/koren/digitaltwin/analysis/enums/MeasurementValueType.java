package com.koren.digitaltwin.analysis.enums;

import lombok.Getter;

@Getter
public enum MeasurementValueType {
    TEMPERATURE(11.11),
    HUMIDITY(10),
    PRESSURE(1),
    TVOC(150),
    SOUND(10),
    LIGHT(150),
    UV(2),
    ECO2(150);

    private final double threshold;

    MeasurementValueType(double threshold) {
        this.threshold = threshold;
    }

}

