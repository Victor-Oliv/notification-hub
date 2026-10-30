package com.notificationhub.notification_hub.repository;

import com.notificationhub.notification_hub.model.Notification;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class NotificationRepository {

    private final Map<Long, Notification> notifications = new ConcurrentHashMap<>();

    private final AtomicLong idGenerator = new AtomicLong(0);


    public Notification save(Notification notification){

        Long id = idGenerator.incrementAndGet();

        notification.setId(id);

        notifications.put(id, notification);

        return notification;
    }

    public Notification findById(Long id){
        return notifications.get(id);
    }
}
