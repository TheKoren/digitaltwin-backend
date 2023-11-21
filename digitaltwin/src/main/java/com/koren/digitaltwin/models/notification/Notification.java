package com.koren.digitaltwin.models.notification;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
/**
 * An interface representing a notification in the digital twin system.
 * Implementations of this interface should provide methods to access and set
 * properties such as ID, unique key, type, and message of the notification.
 */
@Document(collection="events")
public interface Notification {
    /**
     * Get the ID of the notification.
     *
     * @return The ID of the notification.
     */
    @Id
    ObjectId getId();

    /**
     * Set the ID of the notification.
     *
     * @param id The ID to set for the notification.
     */
    void setId(ObjectId id);

    /**
     * Get the unique key associated with the notification.
     *
     * @return The unique key of the notification.
     */
    @Field
    String getUniqueKey();

    /**
     * Set the unique key for the notification.
     *
     * @param uniqueKey The unique key to set for the notification.
     */
    void setUniqueKey(String uniqueKey);

    /**
     * Get the type of the notification.
     *
     * @return The type of the notification.
     */
    @Field
    NotificationType getType();

    /**
     * Set the type of the notification.
     *
     * @param type The type to set for the notification.
     */
    void setType(NotificationType type);

    /**
     * Get the message associated with the notification.
     *
     * @return The message of the notification.
     */
    @Field
    String getMessage();

    /**
     * Set the message for the notification.
     *
     * @param message The message to set for the notification.
     */
    void setMessage(String message);
}
