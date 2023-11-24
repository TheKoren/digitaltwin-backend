package com.koren.digitaltwin.models.notification;

import com.koren.digitaltwin.analysis.enums.MeasurementValueType;
import com.koren.digitaltwin.models.message.WifiMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode(callSuper = true)
@Document(collection="events")
@TypeAlias("drop")
@Data
@NoArgsConstructor
public class DropNotification extends AbstractNotification {

    @Field
    MeasurementValueType measurementValueType;
    @Field
    Number oldValue;
    @Field
    Number newValue;
    public DropNotification(NotificationType type, String message, WifiMessage parent, MeasurementValueType measurementValueType, Number oldValue, Number newValue) {
        super(type, message, parent);
        this.parent = parent;
        this.measurementValueType = measurementValueType;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
}
