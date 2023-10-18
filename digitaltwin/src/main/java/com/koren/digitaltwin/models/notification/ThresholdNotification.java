package com.koren.digitaltwin.models.notification;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="events")
@TypeAlias("threshold")
public class ThresholdNotification extends AbstractNotification {
    ThresholdNotification(NotificationType type, String message) {
        super(type, message);
    }
}
