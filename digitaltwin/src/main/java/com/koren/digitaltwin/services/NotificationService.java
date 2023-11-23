package com.koren.digitaltwin.services;

import com.koren.digitaltwin.models.notification.*;
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
     * Retrieves all {@link DropNotification} from the repository.
     *
     * @return List of all {@link DropNotification} objects.
     */
    public List<Notification> getDropNotifications() {
        return notificationRepository
                .findAll()
                .stream()
                .filter(it -> it instanceof DropNotification)
                .toList();
    }

    /**
     * Retrieves all {@link ThresholdNotification} from the repository.
     *
     * @return List of all {@link ThresholdNotification} objects.
     */
    public List<Notification> getThresholdNotifications() {
        return notificationRepository
                .findAll()
                .stream()
                .filter(it -> it instanceof ThresholdNotification)
                .toList();
    }

    /**
     * Retrieves all {@link ModelChangeNotification} from the repository.
     *
     * @return List of all {@link ModelChangeNotification} objects.
     */
    public List<Notification> getModelChangeNotifications() {
        return notificationRepository
                .findAll()
                .stream()
                .filter(it -> it instanceof ModelChangeNotification)
                .toList();
    }

    /**
     * Retrieves all {@link CrashNotification} from the repository.
     *
     * @return List of all {@link CrashNotification} objects.
     */
    public List<Notification> getCrashNotifications() {
        return notificationRepository
                .findAll()
                .stream()
                .filter(it -> it instanceof CrashNotification)
                .toList();
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
