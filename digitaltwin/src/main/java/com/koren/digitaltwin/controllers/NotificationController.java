package com.koren.digitaltwin.controllers;

import com.koren.digitaltwin.models.notification.Notification;
import com.koren.digitaltwin.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/event")
public class NotificationController {

    @Autowired
    private final NotificationService notificationService;
    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return new ResponseEntity<>(notificationService.allNotification(), HttpStatus.OK);
    }

    @PostMapping("/del")
    public void deleteNotification(@RequestBody String uniqueKey) {
        notificationService.deleteNotification(uniqueKey);
    }
}
