package com.koren.digitaltwin.models.notification;

import com.koren.digitaltwin.models.message.WifiMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A specific type of notification representing a model change event in the digital twin system.
 * Extends the AbstractNotification class and includes additional functionality or properties
 * specific to model change notifications.
 */
@EqualsAndHashCode(callSuper = true)
@Document(collection="events")
@TypeAlias("modelChange")
@Data
@NoArgsConstructor
public class ModelChangeNotification extends AbstractNotification {
    @Field
    WifiMessage parent;
    /**
     * Constructs a ModelChangeNotification with the specified type, message, and parent WifiMessage.
     *
     * @param type    The type of the notification.
     * @param message The message associated with the notification.
     * @param parent  The parent WifiMessage associated with the model change.
     */
    public ModelChangeNotification(NotificationType type, String message, WifiMessage parent) {
        super(type, message);
        this.parent = parent;
    }
}
