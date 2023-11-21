package com.koren.digitaltwin.models.notification;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * A specific type of notification representing a crash event in the digital twin system.
 * Extends the AbstractNotification class and includes additional functionality or properties
 * specific to crash notifications.
 */
@EqualsAndHashCode(callSuper = true)
@Document(collection="events")
@TypeAlias("threshold")
@Data
@NoArgsConstructor
public class CrashNotification extends AbstractNotification {
    /**
     * Constructs a CrashNotification with the specified type and message.
     *
     * @param type    The type of the notification.
     * @param message The message associated with the notification.
     */
    public CrashNotification(NotificationType type, String message) {
        super(type, message);
    }
}