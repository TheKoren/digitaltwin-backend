package com.koren.digitaltwin.models;

import com.koren.digitaltwin.models.message.WifiMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
public class LiveModel {
    private List<WifiMessage> liveMessages = new ArrayList<>();

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

            if (messageTime.isBefore(currentTime.minusSeconds(120))) {
                iterator.remove();
                System.out.printf("Removed message with MAC " + message.getMac());
            }
        }
    }
}
