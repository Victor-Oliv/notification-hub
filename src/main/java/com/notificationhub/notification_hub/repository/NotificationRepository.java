package com.notificationhub.notification_hub.repository;

import com.notificationhub.notification_hub.enumeracao.NotificationChannel;
import com.notificationhub.notification_hub.enumeracao.NotificationStatus;
import com.notificationhub.notification_hub.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByStatus(NotificationStatus status);

    Page<Notification> findByStatus(
            NotificationStatus status,
            Pageable pageable);

    List<Notification> findByChannel(NotificationChannel channel);

    Page<Notification> findByChannel(
            NotificationChannel channel,
            Pageable pageable
    );

    List<Notification> findByChannelAndStatus(
            NotificationChannel channel,
            NotificationStatus status
    );

    Page<Notification> findByChannelAndStatus(
            NotificationChannel channel,
            NotificationStatus status,
            Pageable pageable
    );

}
