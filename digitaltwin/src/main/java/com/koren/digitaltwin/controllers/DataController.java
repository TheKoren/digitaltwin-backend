package com.koren.digitaltwin.controllers;

import com.koren.digitaltwin.models.LiveModel;
import com.koren.digitaltwin.models.message.AbstractWifiMessage;
import com.koren.digitaltwin.models.message.MonitorWifiMessage;
import com.koren.digitaltwin.models.message.NodeWifiMessage;
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
    public ResponseEntity<List<NodeWifiMessage>> getAllData() {
        return new ResponseEntity<>(dataService.allData(), HttpStatus.OK);
    }

    /**
     * Retrieves the latest Wifi message for the specified MAC address.
     *
     * @param mac The MAC address to retrieve the latest Wifi message for.
     * @return ResponseEntity containing the optional latest Wifi message.
     */
    @GetMapping("/{mac}")
    public ResponseEntity<Optional<AbstractWifiMessage>> getLatestData(@PathVariable String mac) {
        return new ResponseEntity<>(dataService.latestData(mac), HttpStatus.OK);
    }

    /**
     * Retrieves the list of live Wifi messages.
     *
     * @return ResponseEntity containing the optional list of live Wifi messages.
     */
    @GetMapping("/live")
    public ResponseEntity<Optional<List<NodeWifiMessage>>> getLiveModel() {
        return new ResponseEntity<>(liveModel.getLiveMessages(), HttpStatus.OK);
    }

    /**
     * Retrieves the MonitorMessage associated with the live model.
     *
     * @return ResponseEntity containing the MonitorMessage.
     */
    @GetMapping("/monitor")
    public ResponseEntity<MonitorWifiMessage> getMonitorMessage() {
        return new ResponseEntity<>(liveModel.getMonitorMessage(), HttpStatus.OK);
    }

    /**
     * Retrieves cluster information associated with the live model.
     *
     * @return ResponseEntity containing the MonitorMessage.
     */
    @GetMapping("/cluster")
    public ResponseEntity<List<List<String>>> getClusterInformation() {
        return new ResponseEntity<>(liveModel.getClusters(), HttpStatus.OK);
    }

    /**
     * Retrieves the set of all MAC addresses in the data.
     *
     * @return ResponseEntity containing the set of all MAC addresses.
     */
    @GetMapping("/all/macs")
    public ResponseEntity<Set<String>> getAllMacs() {
        return new ResponseEntity<>(dataService.allMacs(), HttpStatus.OK);
    }

    /**
     * Retrieves a list of Wifi messages for the specified MAC address.
     *
     * @param mac The MAC address to retrieve the Wifi messages for.
     * @return ResponseEntity containing the list of Wifi messages.
     */
    @GetMapping("/all/{mac}")
    public ResponseEntity<List<AbstractWifiMessage>> getWifiMessageByMac(@PathVariable String mac) {
        return new ResponseEntity<>(dataService.wifiMessageByMac(mac), HttpStatus.OK);
    }

    /**
     * Retrieves a list of Wifi messages for the specified MAC address and number.
     *
     * @param mac The MAC address to retrieve the Wifi messages for.
     * @param num The number of messages to retrieve.
     * @return ResponseEntity containing the list of Wifi messages.
     */
    @GetMapping("/all/{mac}/{num}")
    public ResponseEntity<List<AbstractWifiMessage>> getWifiMessageByMacAndNumber(@PathVariable String mac, @PathVariable int num) {
        return new ResponseEntity<>(dataService.wifiMessageByMacAndNumber(mac, num), HttpStatus.OK);
    }

    /**
     * Deletes all data.
     *
     * @return ResponseEntity indicating the success of data deletion.
     */
    @GetMapping("/test/del")
    public ResponseEntity<String> deleteAll() {
        return new ResponseEntity<>(dataService.deleteAll(), HttpStatus.OK);
    }
}
