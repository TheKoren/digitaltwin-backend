package com.koren.digitaltwin.services;

import com.koren.digitaltwin.models.message.AbstractWifiMessage;
import com.koren.digitaltwin.models.message.NodeWifiMessage;
import com.koren.digitaltwin.repositories.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for managing Wi-Fi messages.
 */
@Service
public class DataService {
    @Autowired
    private DataRepository dataRepository;

    /**
     * Retrieves all Wi-Fi messages.
     *
     * @return List of all Wi-Fi messages.
     */
    public List<NodeWifiMessage> allData() {
        return dataRepository.findAll();
    }

    /**
     * Retrieves the latest Wi-Fi message for a specific MAC address.
     *
     * @param mac MAC address of the Wi-Fi message.
     * @return Optional containing the latest Wi-Fi message if found, otherwise an empty optional.
     */
    public Optional<AbstractWifiMessage> latestData(String mac) {
        return dataRepository.findMessagesByMac(mac)
                .stream()
                .max(Comparator.comparingLong(message -> message.timestamp.toEpochMilli()));
    }

    /**
     * Retrieves all unique MAC addresses from the Wi-Fi messages.
     *
     * @return Set of all unique MAC addresses.
     */
    public Set<String> allMacs() {
        List<NodeWifiMessage> allMessages = dataRepository.findAll();
        return allMessages
                .stream()
                .map(NodeWifiMessage::getMac)
                .collect(Collectors.toSet());
    }

    /**
     * Saves a Wi-Fi message to the repository.
     *
     * @param message Wi-Fi message to be saved.
     */
    public void saveData(NodeWifiMessage message) {
        dataRepository
                .save(message);
    }

    /**
     * Retrieves a list of messages by MAC address.
     *
     * @param mac MAC address of the messages.
     * @return List of messages with the specified MAC address.
     */
    public List<AbstractWifiMessage> wifiMessageByMac(String mac) {
        return dataRepository
                .findMessagesByMac(mac);
    }

    /**
     * Retrieves a specific number of recent messages by MAC address.
     *
     * @param mac MAC address of the messages.
     * @param num Number of recent messages to retrieve.
     * @return List of recent messages with the specified MAC address.
     */
    public List<AbstractWifiMessage> wifiMessageByMacAndNumber(String mac, int num) {
        List<AbstractWifiMessage> abstractWifiMessages = dataRepository.findMessagesByMac(mac);
        return abstractWifiMessages.size() <= num ? abstractWifiMessages : abstractWifiMessages.subList(abstractWifiMessages.size() - num, abstractWifiMessages.size());
    }

    /**
     * Deletes all Wi-Fi messages from the repository.
     *
     * @return A message indicating the operation completion status.
     */
    public String deleteAll() {
        dataRepository.deleteAll();
        return "Done";
    }
}
