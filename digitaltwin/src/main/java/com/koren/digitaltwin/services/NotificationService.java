package com.koren.digitaltwin.services;

import com.koren.digitaltwin.models.notification.Notification;
import com.koren.digitaltwin.models.notification.ModelChangeNotification;
import com.koren.digitaltwin.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing notifications.
 */
@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    /**
     * Retrieves all notifications from the repository.
     *
     * @return List of all notifications.
     */
    public List<Notification> allNotification() {
        return notificationRepository.findAll();
    }

    /**
     * Saves a notification to the repository.
     *
     * @param notification Notification to be saved.
     */
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    /**
     * Deletes a notification from the repository based on its unique key.
     *
     * @param uniqueKey Unique key of the notification to be deleted.
     */
    public void deleteNotification(String uniqueKey) {
        var notification = notificationRepository.findAll().stream()
                .filter(n -> n instanceof ModelChangeNotification)
                .filter(n -> ((ModelChangeNotification) n).getUniqueKey().equals(uniqueKey.replaceAll(".$", "")))
                .findFirst().get();
        notificationRepository.deleteById(notification.getId());
    }
}
