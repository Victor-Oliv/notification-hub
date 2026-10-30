package com.notificationhub.notification_hub.controller;

import com.notificationhub.notification_hub.dto.NotificationRequestDTO;
import com.notificationhub.notification_hub.model.Notification;
import com.notificationhub.notification_hub.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<Notification> postNotification(
            @RequestBody @Valid NotificationRequestDTO notificationRequestDTO
    ){
        Notification notification =
                notificationService.create(notificationRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notification);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotification(
            @PathVariable Long id
    ){
        Notification notification =
                notificationService.findById(id);

        return ResponseEntity.ok(notification);
    }
}
