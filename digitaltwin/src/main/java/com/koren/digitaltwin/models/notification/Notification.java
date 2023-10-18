package com.koren.digitaltwin.models.notification;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="events")
public interface Notification {
    @Id
    ObjectId getId();
    void setId(ObjectId id);

    @Field
    String getUniqueKey();
    void setUniqueKey(String uniqueKey);

    @Field
    NotificationType getType();
    void setType(NotificationType type);

    @Field
    String getMessage();
    void setMessage(String message);
}
