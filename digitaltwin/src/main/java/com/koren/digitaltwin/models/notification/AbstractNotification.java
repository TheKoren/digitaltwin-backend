package com.koren.digitaltwin.models.notification;

import com.koren.digitaltwin.models.message.WifiMessage;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * An abstract class serving as a base for specific notification implementations.
 * Implements the Notification interface and provides common properties and functionality
 * for all notifications in the digital twin system.
 */
@Data
@NoArgsConstructor
public abstract class AbstractNotification implements Notification {
    private ObjectId id;
    private String uniqueKey;
    private NotificationType type;
    private String message;
    @Field
    WifiMessage parent;

    /**
     * Constructs an AbstractNotification with the specified type and message.
     * Automatically generates a unique identifier and unique key for the notification.
     *
     * @param type    The type of the notification.
     * @param message The message associated with the notification.
     */
    AbstractNotification(NotificationType type, String message, WifiMessage parent) {
        this.type = type;
        this.message = message;
        this.id = new ObjectId();
        this.uniqueKey = id.toString();
        this.parent = parent;
    }
}
