package com.notificationhub.notification_hub.provider;

import com.notificationhub.notification_hub.model.Notification;

public interface NotificationProvider {

    void sendNotification(Notification notification);
}
