package com.notificationhub.notification_hub.service;

import com.notificationhub.notification_hub.dto.NotificationRequestDTO;
import com.notificationhub.notification_hub.exception.NotificationNotFoundException;
import com.notificationhub.notification_hub.model.Notification;
import com.notificationhub.notification_hub.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification create(NotificationRequestDTO request){

       Notification notification = new Notification(
               request.recipient(),
               request.message(),
               request.channel());

       notificationRepository.save(notification);

        return notification;
    }

    public Notification findById(Long id){

        Notification notification = notificationRepository.findById(id);

        if(notification == null){
           throw new NotificationNotFoundException(id);
        }

        return notification;
    }
}
