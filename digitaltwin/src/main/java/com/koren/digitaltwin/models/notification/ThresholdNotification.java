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
@TypeAlias("threshold")
@Data
@NoArgsConstructor
public class ThresholdNotification extends AbstractNotification {
    @Field
    Long difference;
    public ThresholdNotification(NotificationType type, String message, WifiMessage parent, long difference) {
        super(type, message, parent);
        this.difference = difference;
    }
}
