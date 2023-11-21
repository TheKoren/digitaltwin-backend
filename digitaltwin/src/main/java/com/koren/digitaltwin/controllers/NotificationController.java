package com.koren.digitaltwin.controllers;

import com.koren.digitaltwin.models.notification.Notification;
import com.koren.digitaltwin.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller handling notification-related functionalities.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/event")
public class NotificationController {

    /** The NotificationService instance for handling notification retrieval and deletion. */
    @Autowired
    private final NotificationService notificationService;

    /**
     * Constructs a NotificationController with the specified dependencies.
     *
     * @param notificationService The NotificationService instance.
     */
    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Retrieves all notifications.
     *
     * @return ResponseEntity containing the list of all notifications.
     */
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return new ResponseEntity<>(notificationService.allNotification(), HttpStatus.OK);
    }

    /**
     * Deletes a notification based on its unique key.
     *
     * @param uniqueKey The unique key of the notification to be deleted.
     */
    @PostMapping("/del")
    public void deleteNotification(@RequestBody String uniqueKey) {
        notificationService.deleteNotification(uniqueKey);
    }
}
