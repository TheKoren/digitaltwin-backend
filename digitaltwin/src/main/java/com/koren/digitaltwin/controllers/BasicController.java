package com.koren.digitaltwin.controllers;

import com.koren.digitaltwin.configuration.Config;
import com.koren.digitaltwin.models.LiveModel;
import com.koren.digitaltwin.models.message.MessageFactory;
import com.koren.digitaltwin.models.message.MonitorMessage;
import com.koren.digitaltwin.models.message.WifiMessage;
import com.koren.digitaltwin.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class BasicController {
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final LiveModel liveModel;

    private final DataService dataService;

    private final Config config;

    @Autowired
    public BasicController(LiveModel liveModel, DataService dataService, Config config) {
        this.liveModel = liveModel;
        this.dataService = dataService;
        this.config = config;
    }

    MessageFactory messageFactory = new MessageFactory();

    @PostMapping("/data")
    @ResponseBody
    public ResponseEntity<String> receiveData (@RequestBody String data){
        System.out.println("Received data: " + data);
        var message = messageFactory.createMessage(data);
        if (message instanceof WifiMessage) {
            dataService.saveData((WifiMessage) message);
            liveModel.updateLiveMessage((WifiMessage) message);
        } else if (message instanceof MonitorMessage) {
            liveModel.setMonitorMessage((MonitorMessage) message);
        }

        return ResponseEntity.ok("Data received successfully");
    }

    @GetMapping("/esp32Devices")
    public ResponseEntity<List<String>> getEsp32Devices() {
        List<String> esp32Devices = config.getEsp32Devices();
        return ResponseEntity.ok(esp32Devices);
    }
}
