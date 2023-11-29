package com.koren.digitaltwin.analysis;

import com.koren.digitaltwin.models.message.data.wifi.enums.WifiMode;
import com.koren.digitaltwin.models.message.NodeWifiMessage;
import com.koren.digitaltwin.models.notification.CrashNotification;
import com.koren.digitaltwin.models.notification.NotificationType;
import com.koren.digitaltwin.models.notification.ThresholdNotification;
import com.koren.digitaltwin.services.DataService;
import com.koren.digitaltwin.services.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

/**
 * This class provides methods for analyzing stability-related aspects of digital twin data.
 * It includes functionality to detect delays and crashes in the system.
 */
@Component
public class StabilityAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(StabilityAnalyzer.class);
    private static final long TIME_THRESHOLD = 12000;
    private static Instant lastDelayTimestamp = Instant.MIN;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private DataService dataService;
    /**
     * Detect delays in the system based on the time differences between consecutive messages.
     *
     * @param messageList The list of WifiMessages to analyze for delays.
     */
    public void detectDelays(List<NodeWifiMessage> messageList) {
        if (messageList.size() < 2) {
            return;
        }
        for (int i = messageList.size() - 1; i >= 1; i--) {
            NodeWifiMessage currentMessage = messageList.get(i - 1);
            if (currentMessage.getTimestamp().isBefore(lastDelayTimestamp)) {
                continue;
            }
            NodeWifiMessage previousMessage = messageList.get(i);
            long timeDifference = currentMessage.getTimestamp().toEpochMilli() - previousMessage.getTimestamp().toEpochMilli();
            if (timeDifference > TIME_THRESHOLD) {
                notificationService.saveNotification(new ThresholdNotification(NotificationType.WARNING, "Device (" + messageList.get(0).getMac() + ") above delay threshold.", currentMessage, timeDifference));
                lastDelayTimestamp = currentMessage.getTimestamp();
            }
        }
    }

    /**
     * Detect crashes in the system by identifying nodes with missing model addresses.
     *
     * @param liveMessages The list of live WifiMessages to analyze for crashes.
     */
    public void detectCrashes(List<NodeWifiMessage> liveMessages) {
        var apNodes = liveMessages
                .stream()
                .filter(it -> it.getWifiData().mode == WifiMode.AP_STA)
                .toList();
        for (NodeWifiMessage node : apNodes) {
            var addressList = node.getWifiData().addressList;
            var modelAddressList = liveMessages
                    .stream()
                    .map(NodeWifiMessage::getMac)
                    .toList();
            for (String address : addressList) {
                if (!modelAddressList.contains(address)) {
                    var latestData = dataService.latestData(address).get();
                    notificationService.saveNotification(new CrashNotification(NotificationType.ERROR, "Possible crash on device." + address, (NodeWifiMessage) latestData, node));
                }
            }
        }
        if (apNodes.isEmpty() && !liveMessages.isEmpty()) {
            for (NodeWifiMessage msg : liveMessages) {
                notificationService.saveNotification(new CrashNotification(NotificationType.ERROR, "AP unable to send data, but end device still available", msg, null));
            }
        }
    }

    /**
     * Calculate statistical measures for a list of time differences.
     *
     * @param timeDifferences The list of time differences to calculate statistics.
     * @return The average of time differences.
     */
    @SuppressWarnings("unused")
    private static long calculateStatistics(List<Long> timeDifferences) {
        if (timeDifferences.isEmpty()) {
            // We shouldn't be here at this point but who knows what goes wrong :-D
            logger.info("No time differences to calculate statistics.");
            return 0;
        }
        long sum = 0;
        for (long difference : timeDifferences) {
            sum+=difference;
        }
        long average = sum / timeDifferences.size();
        long lowest = Collections.min(timeDifferences);
        long highest = Collections.max(timeDifferences);
        Collections.sort(timeDifferences);
        long median;
        if (timeDifferences.size() % 2 == 0) {
            median = (((timeDifferences.get(timeDifferences.size() / 2 - 1)) + (timeDifferences.get(timeDifferences.size() / 2 + 1))) / 2);
        } else {
            median = timeDifferences.get(timeDifferences.size() / 2);
        }
        logger.info("Average time difference: " + average + " milliseconds");
        logger.info("Lowest time difference: " + lowest + " milliseconds");
        logger.info("Highest time difference: " + highest + " milliseconds");
        logger.info("Median time difference: " + median + " milliseconds");
        return average;
    }
}