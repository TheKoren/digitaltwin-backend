package com.koren.digitaltwin.models;

import com.koren.digitaltwin.analysis.ClusterAnalyzer;
import com.koren.digitaltwin.analysis.DropAnalyzer;
import com.koren.digitaltwin.analysis.StabilityAnalyzer;
import com.koren.digitaltwin.models.message.MonitorWifiMessage;
import com.koren.digitaltwin.models.message.NodeWifiMessage;
import com.koren.digitaltwin.models.notification.ModelChangeNotification;
import com.koren.digitaltwin.models.notification.NotificationType;
import com.koren.digitaltwin.services.DataService;
import com.koren.digitaltwin.services.NotificationService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(LiveModel.class);
    private static final int MAX_MESSAGES_PER_MAC = 20;
    private static final long INACTIVE_NODE_THRESHOLD_HOURS = 2;
    private List<NodeWifiMessage> liveMessages = new ArrayList<>();
    @Getter
    private Map<String, List<NodeWifiMessage>> messagesForAnalysis = new HashMap<>();

    @Autowired
    private StabilityAnalyzer stabilityAnalyzer;
    @Autowired
    private DropAnalyzer dropAnalyzer;
    @Autowired
    private ClusterAnalyzer clusterAnalyzer;

    @Getter
    @Setter
    private MonitorWifiMessage monitorMessage;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private DataService dataService;

    /**
     * Retrieves the optional list of live Wifi messages.
     *
     * @return Optional containing the list of live Wifi messages.
     */
    public Optional<List<NodeWifiMessage>> getLiveMessages() {
        return Optional.of(liveMessages);
    }

    /**
     * Updates the live messages and the analysis map with the given Wifi message.
     *
     * @param message The Wifi message to be added or updated.
     */
    public void updateLiveMessage(NodeWifiMessage message) {
        updateMessagesForAnalysis(message);
        updateLiveMessagesList(message);
    }

    private void updateMessagesForAnalysis(NodeWifiMessage message) {
        String mac = message.getMac();
        List<NodeWifiMessage> macMessages = messagesForAnalysis.computeIfAbsent(mac, k -> new LinkedList<>());

        if (macMessages.size() == MAX_MESSAGES_PER_MAC) {
            macMessages.remove(macMessages.size() - 1);
        }
        macMessages.add(0, message);
    }

    private void updateLiveMessagesList(NodeWifiMessage message) {
        Iterator<NodeWifiMessage> iterator = liveMessages.iterator();
        boolean found = false;

        while (iterator.hasNext()) {
            NodeWifiMessage existingMessage = iterator.next();
            if (existingMessage.getMac().equals(message.getMac())) {
                iterator.remove();
                found = true;
                break;
            }
        }

        liveMessages.add(message);

        if (found) {
            logger.info("Updated existing message for MAC: {}", message.getMac());
        } else {
            logger.info("Added new message for MAC: {}", message.getMac());
        }
    }

    /**
     * Scheduled method to check network nodes, remove inactive nodes, and conduct stability analysis.
     */
    @Scheduled(fixedRate = 60000)
    public void checkNetworkNodes() {
        Instant currentTime = Instant.now();
        Iterator<NodeWifiMessage> iterator = liveMessages.iterator();

        while(iterator.hasNext()) {
            NodeWifiMessage message = iterator.next();
            Instant messageTime = message.getTimestamp();

            if (messageTime.isBefore(currentTime.plus(INACTIVE_NODE_THRESHOLD_HOURS, ChronoUnit.HOURS).minusSeconds(60))) {
                iterator.remove();
                notifyInactiveNode(message);
            }
        }

        conductAnalysis();
    }

    private void notifyInactiveNode(NodeWifiMessage message) {
        notificationService.saveNotification(new ModelChangeNotification(NotificationType.WARNING, "Device lost: " + message.getMac(), message));
    }

    private void conductAnalysis() {
        stabilityAnalyzer.detectCrashes(liveMessages);
        clusterAnalyzer.detectClusters(liveMessages);

        liveMessages.stream()
                .map(NodeWifiMessage::getMac)
                .forEach(mac -> {
                    stabilityAnalyzer.detectDelays(messagesForAnalysis.get(mac));
                    dropAnalyzer.detectDrops(messagesForAnalysis.get(mac));
                });
    }


    /**
     * Retrieves the current cluster information from the livemodel
     *
     * @return List containing list of MAC addresses to depict the cluster hierarchy.
     */
    public List<List<String>> getClusters()
    {
        return clusterAnalyzer.getInternalCache();
    }

}
