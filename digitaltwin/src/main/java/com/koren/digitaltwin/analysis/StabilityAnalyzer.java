package com.koren.digitaltwin.analysis;

import com.koren.digitaltwin.models.enums.WifiMode;
import com.koren.digitaltwin.models.message.Message;
import com.koren.digitaltwin.models.message.WifiMessage;
import com.koren.digitaltwin.models.notification.CrashNotification;
import com.koren.digitaltwin.models.notification.NotificationType;
import com.koren.digitaltwin.models.notification.ThresholdNotification;
import com.koren.digitaltwin.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class provides methods for analyzing stability-related aspects of digital twin data.
 * It includes functionality to detect delays and crashes in the system.
 */
@Component
public class StabilityAnalyzer {
    private static final long TIME_TRESHOLD = 2000;
    @Autowired
    private NotificationService notificationService;
    static List<Long> timeDifferences = new ArrayList<>();
    /**
     * Detect delays in the system based on the time differences between consecutive messages.
     *
     * @param messageList The list of WifiMessages to analyze for delays.
     */
    public void detectDelays(List<WifiMessage> messageList) {
        if (messageList.size() < 2) {
            return;
        }
        for (int i = 1; i < messageList.size(); i++) {
            Message previousMessage = messageList.get(i);
            Message currentMessage = messageList.get(i - 1);

            long timeDifference = currentMessage.getTimestamp().toEpochMilli() - previousMessage.getTimestamp().toEpochMilli();
            timeDifferences.add(timeDifference);
        }
        var avg = calculateStatistics(timeDifferences);
        for (Long epoch : timeDifferences) {
            if (!(epoch > (avg - TIME_TRESHOLD) && epoch < (avg + TIME_TRESHOLD))) {
                notificationService.saveNotification(new ThresholdNotification(NotificationType.WARNING, "Device (" + messageList.get(0).getMac() + ") above delay threshold: " + epoch));
            }
        }
    }

    /**
     * Calculate statistical measures for a list of time differences.
     *
     * @param timeDifferences The list of time differences to calculate statistics.
     * @return The average of time differences.
     */
    private static long calculateStatistics(List<Long> timeDifferences) {
        if (timeDifferences.isEmpty()) {
            // We shouldn't be here at this point but who knows what goes wrong :-D
            System.out.println("No time differences to calculate statistics.");
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
        System.out.println("Average time difference: " + average + " milliseconds");
        System.out.println("Lowest time difference: " + lowest + " milliseconds");
        System.out.println("Highest time difference: " + highest + " milliseconds");
        System.out.println("Median time difference: " + median + " milliseconds");
        return average;
    }

    /**
     * Detect crashes in the system by identifying nodes with missing model addresses.
     *
     * @param liveMessages The list of live WifiMessages to analyze for crashes.
     */
    public void detectCrashes(List<WifiMessage> liveMessages) {
        var apNodes = liveMessages
                .stream()
                .filter(it -> it.getWifiData().mode == WifiMode.AP_STA)
                .toList();
        for (WifiMessage node : apNodes) {
            var addressList = node.getWifiData().addressList;
            var modelAddressList = liveMessages
                    .stream()
                    .map(WifiMessage::getMac)
                    .toList();
            for (String address : addressList) {
                if (!modelAddressList.contains(address)) {
                    notificationService.saveNotification(new CrashNotification(NotificationType.ERROR, "Possible crash on device: " + address));
                }
            }
        }
    }
}