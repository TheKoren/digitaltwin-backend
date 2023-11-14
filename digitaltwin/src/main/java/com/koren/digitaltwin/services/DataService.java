package com.koren.digitaltwin.services;

import com.koren.digitaltwin.models.message.Message;
import com.koren.digitaltwin.models.message.WifiMessage;
import com.koren.digitaltwin.repositories.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DataService {
    @Autowired
    private DataRepository dataRepository;
    public List<WifiMessage> allData() {
        return dataRepository.findAll();
    }

    public Optional<WifiMessage> latestData(String mac) {
        return dataRepository.findMessageByMac(mac).stream().max(Comparator.comparingLong(message -> message.timestamp.toEpochMilli()));
    }

    public Set<String> allMacs() {
        List<WifiMessage> allMessages = dataRepository.findAll();
        return allMessages.stream().map(WifiMessage::getMac).collect(Collectors.toSet());
    }

    public void saveData(WifiMessage message) {
        dataRepository.save(message);
    }

    public List<Message> wifiMessageByMac(String mac) {
        return dataRepository.findMessagesByMac(mac);
    }

    public List<Message> wifiMessageByMacAndNumber(String mac, int num) {
        List<Message> messages = dataRepository.findMessagesByMac(mac);
        return messages.size() <= num ? messages : messages.subList(messages.size() - num, messages.size());
    }

    public String deleteAll() {
        dataRepository.deleteAll();
        return "Done";
    }
}
