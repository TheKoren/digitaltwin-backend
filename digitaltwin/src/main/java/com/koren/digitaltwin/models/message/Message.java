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

/**
 * Represents a generic message with common properties such as id, MAC address, and timestamp.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
abstract public class Message {

    /** Unique identifier for the message. */
    @Id
    private ObjectId id;

    /** MAC address associated with the message. */
    public String mac;

    /** Timestamp indicating when the message was created. */
    public Instant timestamp;

    /**
     * Constructs a Message instance with the provided ID and MAC address.
     *
     * @param id  Unique identifier for the message.
     * @param mac MAC address associated with the message.
     */
    Message(ObjectId id, String mac) {
        this.id = id;
        this.mac = mac;
        this.timestamp = ZonedDateTime.now(ZoneId.systemDefault()).toInstant().plus(1, ChronoUnit.HOURS);
    }
}
