package com.koren.digitaltwin.analysis.enums;

import lombok.Getter;

@SuppressWarnings("unused")
@Getter
public enum StabilityCheckType {
    CRASH(0),
    DELAY(2000);

    private final double threshold;

    StabilityCheckType(double threshold) {this.threshold = threshold;}

}
