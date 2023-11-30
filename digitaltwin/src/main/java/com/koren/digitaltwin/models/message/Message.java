package com.koren.digitaltwin.models.message;

import org.bson.types.ObjectId;

import java.time.Instant;

/**
 * The {@code Message} interface represents a data structure for storing information related to a message from an IoT device.
 * It defines methods to retrieve the unique identifier, MAC address, and timestamp of the message.
 * Implementing classes should provide concrete implementations for these methods.
 *
 * @author TheKoren
 * @version 1.0
 * @since 2023.11.21
 */
public interface Message {
    /**
     * Retrieves the unique identifier of the message.
     *
     * @return The unique identifier of the message.
     */
    ObjectId getId();

    /**
     * Retrieves the MAC address associated with the message.
     *
     * @return The MAC address associated with the message.
     */
    String getMac();

    /**
     * Retrieves the timestamp indicating when the message was created.
     *
     * @return The timestamp of the message creation.
     */
    Instant getTimestamp();
}
