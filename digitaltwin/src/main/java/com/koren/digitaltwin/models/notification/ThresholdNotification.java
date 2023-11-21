package com.koren.digitaltwin.models.notification;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
@EqualsAndHashCode(callSuper = true)
@Document(collection="events")
@TypeAlias("threshold")
@Data
@NoArgsConstructor
public class ThresholdNotification extends AbstractNotification {
    public ThresholdNotification(NotificationType type, String message) {
        super(type, message);
    }
}
