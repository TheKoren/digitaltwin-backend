package com.koren.digitaltwin.models.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
abstract public class Message {
    @Id
    private ObjectId id;
    public String mac;
    public Instant timestamp;

    Message(ObjectId id, String mac) {
        this.id = id;
        this.mac = mac;
        this.timestamp = ZonedDateTime.now(ZoneId.systemDefault()).toInstant().plus(1, ChronoUnit.HOURS);
    }
}
