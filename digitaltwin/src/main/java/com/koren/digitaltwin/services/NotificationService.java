package com.koren.digitaltwin.services;

import com.koren.digitaltwin.models.notification.Notification;
import com.koren.digitaltwin.models.notification.ModelChangeNotification;
import com.koren.digitaltwin.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> allNotification() {
        return notificationRepository.findAll();}

    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public void deleteNotification(String uniqueKey) {
        var notification = notificationRepository.findAll().stream()
                .filter(n -> n instanceof ModelChangeNotification)
                .filter(n -> ((ModelChangeNotification) n).getUniqueKey().equals(uniqueKey.replaceAll(".$","")))
                .findFirst().get();
        notificationRepository.deleteById(notification.getId());
    }
}
