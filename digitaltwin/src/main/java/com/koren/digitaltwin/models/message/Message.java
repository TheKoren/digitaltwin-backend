package com.koren.digitaltwin.models.message;

import org.bson.types.ObjectId;

import java.time.Instant;

public interface Message {
    ObjectId getId();

    String getMac();

    Instant getTimestamp();
}
