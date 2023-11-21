package com.koren.digitaltwin.repositories;

import com.koren.digitaltwin.models.notification.Notification;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing notifications in MongoDB.
 */
@Repository
public interface NotificationRepository extends MongoRepository<Notification, ObjectId> {

    /**
     * Retrieves all notifications from the repository.
     *
     * @return A list of all notifications in the repository.
     */
    List<Notification> findAll();
}
