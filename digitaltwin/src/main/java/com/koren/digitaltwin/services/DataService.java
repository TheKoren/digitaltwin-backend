package com.koren.digitaltwin.services;

import com.koren.digitaltwin.models.message.WifiMessage;
import com.koren.digitaltwin.repositories.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

    public void saveData(WifiMessage message) {
        dataRepository.save(message);
    }
}
