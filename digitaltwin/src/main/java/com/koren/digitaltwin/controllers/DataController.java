package com.koren.digitaltwin.controllers;

import com.koren.digitaltwin.models.LiveModel;
import com.koren.digitaltwin.models.message.Message;
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

/**
 * Controller handling data-related functionalities.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/data")
public class DataController {

    /** The DataService instance for handling data retrieval. */
    @Autowired
    private final DataService dataService;

    /** The LiveModel instance managing live messages. */
    private final LiveModel liveModel;

    /**
     * Constructs a DataController with the specified dependencies.
     *
     * @param dataService The DataService instance.
     * @param liveModel   The LiveModel instance.
     */
    @Autowired
    public DataController(DataService dataService, LiveModel liveModel) {
        this.dataService = dataService;
        this.liveModel = liveModel;
    }

    /**
     * Retrieves all Wifi messages.
     *
     * @return ResponseEntity containing the list of all Wifi messages.
     */
    @GetMapping
    public ResponseEntity<List<WifiMessage>> getAllData() {
        return new ResponseEntity<List<WifiMessage>>(dataService.allData(), HttpStatus.OK);
    }

    /**
     * Retrieves the latest Wifi message for the specified MAC address.
     *
     * @param mac The MAC address to retrieve the latest Wifi message for.
     * @return ResponseEntity containing the optional latest Wifi message.
     */
    @GetMapping("/{mac}")
    public ResponseEntity<Optional<WifiMessage>> getLatestData(@PathVariable String mac) {
        return new ResponseEntity<Optional<WifiMessage>>(dataService.latestData(mac), HttpStatus.OK);
    }

    /**
     * Retrieves the list of live Wifi messages.
     *
     * @return ResponseEntity containing the optional list of live Wifi messages.
     */
    @GetMapping("/live")
    public ResponseEntity<Optional<List<WifiMessage>>> getLiveModel() {
        return new ResponseEntity<Optional<List<WifiMessage>>>(liveModel.getLiveMessages(), HttpStatus.OK);
    }

    /**
     * Retrieves the MonitorMessage associated with the live model.
     *
     * @return ResponseEntity containing the MonitorMessage.
     */
    @GetMapping("/monitor")
    public ResponseEntity<MonitorMessage> getMonitorMessage() {
        return new ResponseEntity<>(liveModel.getMonitorMessage(), HttpStatus.OK);
    }

    /**
     * Retrieves the set of all MAC addresses in the data.
     *
     * @return ResponseEntity containing the set of all MAC addresses.
     */
    @GetMapping("/all/macs")
    public ResponseEntity<Set<String>> getAllMacs() {
        return new ResponseEntity<Set<String>>(dataService.allMacs(), HttpStatus.OK);
    }

    /**
     * Retrieves a list of Wifi messages for the specified MAC address.
     *
     * @param mac The MAC address to retrieve the Wifi messages for.
     * @return ResponseEntity containing the list of Wifi messages.
     */
    @GetMapping("/all/{mac}")
    public ResponseEntity<List<Message>> getWifiMessageByMac(@PathVariable String mac) {
        return new ResponseEntity<List<Message>>(dataService.wifiMessageByMac(mac), HttpStatus.OK);
    }

    /**
     * Retrieves a list of Wifi messages for the specified MAC address and number.
     *
     * @param mac The MAC address to retrieve the Wifi messages for.
     * @param num The number of messages to retrieve.
     * @return ResponseEntity containing the list of Wifi messages.
     */
    @GetMapping("/all/{mac}/{num}")
    public ResponseEntity<List<Message>> getWifiMessageByMacAndNumber(@PathVariable String mac, @PathVariable int num) {
        return new ResponseEntity<List<Message>>(dataService.wifiMessageByMacAndNumber(mac, num), HttpStatus.OK);
    }

    /**
     * Deletes all data.
     *
     * @return ResponseEntity indicating the success of data deletion.
     */
    @GetMapping("/test/del")
    public ResponseEntity<String> deleteAll() {
        return new ResponseEntity<String>(dataService.deleteAll(), HttpStatus.OK);
    }
}
