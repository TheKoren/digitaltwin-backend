package com.koren.digitaltwin.controllers;

import com.koren.digitaltwin.models.notification.*;
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
     * Retrieves all {@link CrashNotification} from the repository.
     *
     * @return List of all {@link CrashNotification} objects.
     */
    @GetMapping("/crash")
    public ResponseEntity<List<Notification>> getAllCrashNotifications() {
        return new ResponseEntity<>(notificationService.getCrashNotifications(), HttpStatus.OK);
    }

    /**
     * Retrieves all {@link ModelChangeNotification} from the repository.
     *
     * @return List of all {@link ModelChangeNotification} objects.
     */
    @GetMapping("/modelchange")
    public ResponseEntity<List<Notification>> getAllModelChangeNotifications() {
        return new ResponseEntity<>(notificationService.getModelChangeNotifications(), HttpStatus.OK);
    }

    /**
     * Retrieves all {@link DropNotification} from the repository.
     *
     * @return List of all {@link DropNotification} objects.
     */
    @GetMapping("/drop")
    public ResponseEntity<List<Notification>> getAllDropNotifications() {
        return new ResponseEntity<>(notificationService.getDropNotifications(), HttpStatus.OK);
    }

    /**
     * Retrieves all {@link ThresholdNotification} from the repository.
     *
     * @return List of all {@link ThresholdNotification} objects.
     */
    @GetMapping("threshold")
    public ResponseEntity<List<Notification>> getAllThresholdNotifications() {
        return new ResponseEntity<>(notificationService.getThresholdNotifications(), HttpStatus.OK);
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
