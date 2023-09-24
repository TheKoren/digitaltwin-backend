package com.koren.digitaltwin.controllers;

import com.koren.digitaltwin.models.DataBundleImpl;
import com.koren.digitaltwin.models.Entry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import static com.koren.digitaltwin.Constants.APP_NAME;

@Deprecated
public class BasicController {
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //private final DataService dataService = new DataService();
    private static int id = 0;
    private int n = 0;

    // GET /api/name
    @ResponseBody
    @GetMapping("/api/name")
    public String apiName() {
        return APP_NAME;
    }

    // GET /api/time
    @ResponseBody
    @GetMapping("/api/time")
    public String apiTime() {
        return dateFormat.format(System.currentTimeMillis());
    }

    // GET /api/n
    @ResponseBody
    @GetMapping("/api/n")
    public int apiN() {
        ++n;
        return n;
    }
    // GET /
    @GetMapping("/")
    public String index(Model model) throws IOException {
        ++n;
        model.addAttribute("appName", APP_NAME);
        model.addAttribute("time", dateFormat.format(System.currentTimeMillis()));
        //model.addAttribute("data", Arrays.toString(repository.findAll().stream().map(Entry::toString).toArray()));
        return "index";
    }

    @GetMapping("/del")
    public String delete() {
        //repository.deleteAll();
        return "index";
    }

    @PostMapping("/data")
    public void receiveData (@RequestBody String data){
        System.out.println("Received data: " + data);
        var dataBundle = new DataBundleImpl(data, id);
        var entry = new Entry(
                new ObjectId(),
                dataBundle.mac,
                dataBundle.timeStamp,
                dataBundle.sensorData.temperateValue,
                dataBundle.sensorData.humidityValue,
                dataBundle.sensorData.pressure,
                dataBundle.sensorData.tvocValue,
                dataBundle.wifiData.rssi,
                dataBundle.wifiData.txPower,
                dataBundle.wifiData.channel,
                dataBundle.wifiData.mode,
                dataBundle.wifiData.addressList
        );
        //repository.save(entry);
        id++;
    }
}
