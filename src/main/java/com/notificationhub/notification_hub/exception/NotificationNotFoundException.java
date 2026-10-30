package com.notificationhub.notification_hub.exception;


public class NotificationNotFoundException extends RuntimeException{

    public NotificationNotFoundException(Long id){
        super("Notification not found with id: " + id);
    }
}
