package com.koren.digitaltwin.models.notification;

import com.koren.digitaltwin.models.message.WifiMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A specific type of notification representing a crash event in the digital twin system.
 * Extends the AbstractNotification class and includes additional functionality or properties
 * specific to crash notifications.
 */
@EqualsAndHashCode(callSuper = true)
@Document(collection="events")
@TypeAlias("crash")
@Data
@NoArgsConstructor
public class CrashNotification extends AbstractNotification {
    @Field
    WifiMessage apNode;
    /**
     * Constructs a CrashNotification with the specified type and message.
     *
     * @param type    The type of the notification.
     * @param message The message associated with the notification.
     */
    public CrashNotification(NotificationType type, String message, WifiMessage parent, WifiMessage apNode) {
        super(type, message, parent);
        this.apNode = apNode;
    }
}