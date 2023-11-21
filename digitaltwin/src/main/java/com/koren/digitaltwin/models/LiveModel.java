package com.koren.digitaltwin.models;

import com.koren.digitaltwin.analysis.StabilityAnalyzer;
import com.koren.digitaltwin.models.message.Message;
import com.koren.digitaltwin.models.message.MonitorMessage;
import com.koren.digitaltwin.models.notification.NotificationType;
import com.koren.digitaltwin.models.notification.ModelChangeNotification;
import com.koren.digitaltwin.models.message.WifiMessage;
import com.koren.digitaltwin.repositories.DataRepository;
import com.koren.digitaltwin.services.DataService;
import com.koren.digitaltwin.services.NotificationService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Represents the live model in the digital twin system, managing live messages and conducting stability analysis.
 */
@Component
public class LiveModel {
    private List<WifiMessage> liveMessages = new ArrayList<>();
    @Getter
    private Map<String, List<WifiMessage>> messagesForAnalysis = new HashMap<>();

    @Autowired
    private StabilityAnalyzer stabilityAnalyzer;

    @Getter
    @Setter
    private MonitorMessage monitorMessage;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private DataService dataService;

    /**
     * Retrieves the optional list of live Wifi messages.
     *
     * @return Optional containing the list of live Wifi messages.
     */
    public Optional<List<WifiMessage>> getLiveMessages() {
        return Optional.of(liveMessages);
    }

    /**
     * Updates the live messages and the analysis map with the given Wifi message.
     *
     * @param message The Wifi message to be added or updated.
     */
    public void updateLiveMessage(WifiMessage message) {
        boolean found = false;
        String mac = message.getMac();
        if (messagesForAnalysis.containsKey(mac)) {
            List<WifiMessage> macMessages = messagesForAnalysis.get(mac);
            if (macMessages.size() == 20) {
                macMessages.remove(macMessages.size() - 1);
            }
            macMessages.add(0, message);
        } else {
            List<WifiMessage> newMacMessages = new LinkedList<>();
            newMacMessages.add(message);
            messagesForAnalysis.put(mac, newMacMessages);
        }
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

    /**
     * Scheduled method to check network nodes, remove inactive nodes, and conduct stability analysis.
     */
    @Scheduled(fixedRate = 60000)
    public void checkNetworkNodes() {
        Instant currentTime = Instant.now();
        Iterator<WifiMessage> iterator = liveMessages.iterator();

        while(iterator.hasNext()) {
            WifiMessage message = iterator.next();
            Instant messageTime = message.getTimestamp();

            if (messageTime.isBefore(currentTime.plus(2, ChronoUnit.HOURS).minusSeconds(60))) {
                iterator.remove();
                notificationService.saveNotification(new ModelChangeNotification(NotificationType.WARNING, "Device lost: " + message.getMac(), message));
            }
        }
        // If node is in the model, conduct analysis
        stabilityAnalyzer.detectCrashes(liveMessages);
        for (String mac : liveMessages.stream().map(WifiMessage::getMac).toList()) {
            stabilityAnalyzer.detectDelays(messagesForAnalysis.get(mac));
        }
    }

}
