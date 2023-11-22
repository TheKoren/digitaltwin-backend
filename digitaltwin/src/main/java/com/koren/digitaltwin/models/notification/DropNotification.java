package com.koren.digitaltwin.models.notification;

import com.koren.digitaltwin.analysis.enums.MeasurementValueType;
import com.koren.digitaltwin.models.message.WifiMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection="events")
@TypeAlias("drop")
@Data
@NoArgsConstructor
public class DropNotification extends AbstractNotification {

    MeasurementValueType measurementValueType;
    WifiMessage parent;
    public DropNotification(NotificationType type, String message, WifiMessage parent, MeasurementValueType measurementValueType) {
        super(type, message);
        this.parent = parent;
        this.measurementValueType = measurementValueType;
    }
}
