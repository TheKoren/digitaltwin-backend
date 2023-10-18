package com.koren.digitaltwin.models.notification;

import com.koren.digitaltwin.models.message.WifiMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode(callSuper = true)
@Document(collection="events")
@TypeAlias("modelChange")
@Data
@NoArgsConstructor
public class ModelChangeNotification extends AbstractNotification {
    @Field
    WifiMessage parent;
    public ModelChangeNotification(NotificationType type, String message, WifiMessage parent) {
        super(type, message);
        this.parent = parent;
    }
}
