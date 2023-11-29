package com.koren.digitaltwin.controllers;

import com.koren.digitaltwin.configuration.Config;
import com.koren.digitaltwin.models.LiveModel;
import com.koren.digitaltwin.models.message.MessageFactory;
import com.koren.digitaltwin.models.message.MonitorWifiMessage;
import com.koren.digitaltwin.models.message.NodeWifiMessage;
import com.koren.digitaltwin.services.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Controller handling basic functionalities for data reception and retrieval.
 */
@Controller
public class NetworkController {
    private static final Logger logger = LoggerFactory.getLogger(NetworkController.class);

    /** The LiveModel instance managing live messages. */
    private final LiveModel liveModel;

    /** The DataService instance for saving and retrieving data. */
    private final DataService dataService;

    /** The application configuration. */
    private final Config config;

    /**
     * Constructs a BasicController with the specified dependencies.
     *
     * @param liveModel   The LiveModel instance.
     * @param dataService The DataService instance.
     * @param config      The application configuration.
     */
    @Autowired
    public NetworkController(LiveModel liveModel, DataService dataService, Config config) {
        this.liveModel = liveModel;
        this.dataService = dataService;
        this.config = config;
    }

    /** MessageFactory instance for creating messages. */
    MessageFactory messageFactory = new MessageFactory();

    /**
     * Handles the reception of data, saves it, and updates the LiveModel.
     *
     * @param data The received data.
     * @return ResponseEntity indicating the success of data reception.
     */
    @PostMapping("/data")
    @ResponseBody
    public ResponseEntity<String> receiveData(@RequestBody String data) {
        logger.info("Received data: " + data);
        var message = messageFactory.createMessage(data);

        if (message instanceof NodeWifiMessage) {
            dataService.saveData((NodeWifiMessage) message);
            liveModel.updateLiveMessage((NodeWifiMessage) message);
        } else if (message instanceof MonitorWifiMessage) {
            liveModel.setMonitorMessage((MonitorWifiMessage) message);
        }

        return ResponseEntity.ok("");
    }

    /**
     * Retrieves the list of configured ESP32 devices.
     *
     * @return ResponseEntity containing the list of ESP32 devices.
     */
    @GetMapping("/esp32Devices")
    public ResponseEntity<List<String>> getEsp32Devices() {
        List<String> esp32Devices = config.getEsp32Devices();
        return ResponseEntity.ok(esp32Devices);
    }
}
