package com.koren.digitaltwin.controllers;

import com.koren.digitaltwin.models.Entry;
import com.koren.digitaltwin.services.DataService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/data")
public class DataController {
    @Autowired
    private DataService dataService;

    @GetMapping
    public ResponseEntity<List<Entry>> getAllData() {
        return new ResponseEntity<List<Entry>>(dataService.allData(),HttpStatus.OK);
    }

    @GetMapping("/{mac}")
    public ResponseEntity<Optional<Entry>> getLatestData(@PathVariable String mac) {
        return new ResponseEntity<Optional<Entry>>(dataService.latestData(mac), HttpStatus.OK);
    }
}
