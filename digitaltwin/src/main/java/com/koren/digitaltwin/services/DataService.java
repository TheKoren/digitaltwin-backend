package com.koren.digitaltwin.services;

import com.koren.digitaltwin.models.Entry;
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
    public List<Entry> allData() {
        return dataRepository.findAll();
    }

    public Optional<Entry> latestData(String mac) {
        return dataRepository.findEntryByMac(mac).stream().max(Comparator.comparingLong(entry -> entry.timeStamp.toEpochMilli()));
    }
}
