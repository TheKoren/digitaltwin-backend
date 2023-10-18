package com.koren.digitaltwin.models.notification;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public abstract class AbstractNotification implements Notification {
    private ObjectId id;
    private String uniqueKey;
    private NotificationType type;
    private String message;

    AbstractNotification(NotificationType type, String message) {
        this.type = type;
        this.message = message;
        this.id = new ObjectId();
        this.uniqueKey = id.toString();
    }
}
