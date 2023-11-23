package com.koren.digitaltwin.analysis;

import com.koren.digitaltwin.models.message.WifiMessage;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class provides methods for analyzing clusters of WifiMessages.
 * It includes functionality to detect similar clusters based on specific criteria.
 */
@Getter
@Component
public class ClusterAnalyzer {
    List<List<String>> internalCache = new ArrayList<>();

    /**
     * Detect clusters of WifiMessages based on specific criteria.
     *
     * @param messageList The list of WifiMessages to analyze for clusters.
     */
    public void detectClusters(List<WifiMessage> messageList) {
        if (messageList.size() < 2) {
            return;
        }
        List<List<WifiMessage>> clusters = new ArrayList<>();
        for (WifiMessage message : messageList) {
            if (clusters.isEmpty()) {
                List<WifiMessage> newCluster = new ArrayList<>();
                newCluster.add(message);
                clusters.add(newCluster);
                continue;
            }
            boolean foundCluster = false;
            for (List<WifiMessage> cluster : clusters) {
                foundCluster = isSimilarToCluster(message, cluster);
                if (foundCluster) {
                    cluster.add(message);
                    break;
                }
            }
            if (!foundCluster) {
                List<WifiMessage> newCluster = new ArrayList<>();
                newCluster.add(message);
                clusters.add(newCluster);
            }
        }
        updateInternalCache(clusters);
    }

    /**
     * Update the internal cache with the clusters' MAC addresses.
     *
     * @param clusters The list of clusters containing WifiMessages.
     */
    private void updateInternalCache(List<List<WifiMessage>> clusters) {
        List<List<String>> newInternalCache = new ArrayList<>();

        for (List<WifiMessage> cluster : clusters) {
            List<String> macs = cluster.stream()
                    .map(WifiMessage::getMac)
                    .collect(Collectors.toList());

            newInternalCache.add(macs);
        }

        if (!newInternalCache.equals(internalCache)) {
            internalCache.clear();
            internalCache.addAll(newInternalCache);
        }
    }

    /**
     * Check if a WifiMessage is similar to the first message in a cluster based on specific criteria.
     *
     * @param currentMessage      The WifiMessage to check for similarity.
     * @param cluster List of messages from the comperable cluster.
     * @return True if there is a clustermessage that is similar; false otherwise.
     */
    private boolean isSimilarToCluster(WifiMessage currentMessage, List<WifiMessage> cluster) {
        for (WifiMessage messageFromCluster : cluster) {
            if (Math.abs(Math.abs(currentMessage.getWifiData().getRssi()) - Math.abs(messageFromCluster.getWifiData().getRssi())) <= 2 &&
                    !(Math.abs(currentMessage.getSensorData().getTemperatureValue() - messageFromCluster.getSensorData().getTemperatureValue()) > 3) &&
                    !(Math.abs(currentMessage.getSensorData().getSound() - messageFromCluster.getSensorData().getSound()) > 10)) {
                return true;
            }
        }
        return false;
    }
}
