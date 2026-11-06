package com.notificationhub.notification_hub.service;

import com.notificationhub.notification_hub.dto.NotificationRequestDTO;
import com.notificationhub.notification_hub.enumeracao.NotificationChannel;
import com.notificationhub.notification_hub.enumeracao.NotificationStatus;
import com.notificationhub.notification_hub.exception.NotificationNotFoundException;
import com.notificationhub.notification_hub.model.Notification;
import com.notificationhub.notification_hub.repository.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));

        return notification;
    }

    public Page<Notification> findAllNotification(
            NotificationStatus notificationStatus,
            NotificationChannel notificationChannel,
            Pageable pageable){

        if(notificationStatus != null && notificationChannel != null){
            return notificationRepository
                    .findByChannelAndStatus(notificationChannel, notificationStatus, pageable);
        }

        if(notificationStatus != null){
            return notificationRepository.findByStatus(notificationStatus, pageable);
        }

        if(notificationChannel != null){
            return notificationRepository.findByChannel(notificationChannel, pageable);
        }

        return notificationRepository.findAll(pageable);
    }
}
