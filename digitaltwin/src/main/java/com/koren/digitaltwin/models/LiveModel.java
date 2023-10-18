package com.koren.digitaltwin.models;

import com.koren.digitaltwin.models.notification.NotificationType;
import com.koren.digitaltwin.models.notification.ModelChangeNotification;
import com.koren.digitaltwin.models.message.WifiMessage;
import com.koren.digitaltwin.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
public class LiveModel {
    private List<WifiMessage> liveMessages = new ArrayList<>();

    @Autowired
    private NotificationService notificationService;

    public Optional<List<WifiMessage>> getLiveMessages() {
        return Optional.of(liveMessages);
    }

    public void updateLiveMessage(WifiMessage message) {
        boolean found = false;
        for (WifiMessage existingMessage : liveMessages) {
            if(existingMessage.getMac().equals(message.getMac())) {
                liveMessages.remove(existingMessage);
                found = true;
                break;
            }
        }

        liveMessages.add(message);

        if (found) {
            System.out.println("Updated existing message for MAC: " + message.getMac());
        } else {
            System.out.println("Added new message for MAC: " + message.getMac());
        }
    }

    @Scheduled(fixedRate = 60000)
    public void checkNetworkNodes() {
        Instant currentTime = Instant.now();
        Iterator<WifiMessage> iterator = liveMessages.iterator();

        while(iterator.hasNext()) {
            WifiMessage message = iterator.next();
            Instant messageTime = message.getTimestamp();

            if (messageTime.isBefore(currentTime.plus(2, ChronoUnit.HOURS).minusSeconds(60))) {
                iterator.remove();
                notificationService.saveNotification(new ModelChangeNotification(NotificationType.WARNING, "Device timeout: " + message.getMac(), message));
            }
        }
    }
}
