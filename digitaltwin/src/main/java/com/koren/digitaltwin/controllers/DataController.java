package com.koren.digitaltwin.controllers;

import com.koren.digitaltwin.models.LiveModel;
import com.koren.digitaltwin.models.message.MonitorMessage;
import com.koren.digitaltwin.models.message.WifiMessage;
import com.koren.digitaltwin.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/data")
public class DataController {
    @Autowired
    private final DataService dataService;

    private final LiveModel liveModel;

    @Autowired
    public DataController(DataService dataService, LiveModel liveModel) {
        this.dataService = dataService;
        this.liveModel = liveModel;
    }

    @GetMapping
    public ResponseEntity<List<WifiMessage>> getAllData() {
        return new ResponseEntity<List<WifiMessage>>(dataService.allData(),HttpStatus.OK);
    }

    @GetMapping("/{mac}")
    public ResponseEntity<Optional<WifiMessage>> getLatestData(@PathVariable String mac) {
        return new ResponseEntity<Optional<WifiMessage>>(dataService.latestData(mac), HttpStatus.OK);
    }

    @GetMapping("/live")
    public ResponseEntity<Optional<List<WifiMessage>>> getLiveModel() {
        return new ResponseEntity<Optional<List<WifiMessage>>>(liveModel.getLiveMessages(), HttpStatus.OK);
    }

    @GetMapping("/monitor")
    public ResponseEntity<MonitorMessage> getMonitorMessage() {
        return new ResponseEntity<>(liveModel.getMonitorMessage(), HttpStatus.OK);
    }

    @GetMapping("/all/macs")
    public ResponseEntity<Set<String>> getAllMacs() {
        return new ResponseEntity<Set<String>>(dataService.allMacs(), HttpStatus.OK);
    }

    @GetMapping("/all/{mac}")
    public ResponseEntity<List<WifiMessage>> getWifiMessageByMac(@PathVariable String mac) {
        return new ResponseEntity<List<WifiMessage>>(dataService.wifiMessageByMac(mac), HttpStatus.OK);
    }

    @GetMapping("/all/{mac}/{num}")
    public ResponseEntity<List<WifiMessage>> getWifiMessageByMacAndNumber(@PathVariable String mac, @PathVariable int num) {
        return new ResponseEntity<List<WifiMessage>>(dataService.wifiMessageByMacAndNumber(mac, num), HttpStatus.OK);
    }

    @GetMapping("/test/del")
    public ResponseEntity<String> deleteAll() {
        return new ResponseEntity<String>(dataService.deleteAll(), HttpStatus.OK);
    }
}
