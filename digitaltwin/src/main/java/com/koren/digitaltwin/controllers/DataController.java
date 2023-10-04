package com.koren.digitaltwin.controllers;

import com.koren.digitaltwin.models.LiveModel;
import com.koren.digitaltwin.models.message.WifiMessage;
import com.koren.digitaltwin.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
}
