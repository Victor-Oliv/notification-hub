package com.notificationhub.notification_hub.provider;

import com.notificationhub.notification_hub.model.Notification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailProvider implements NotificationProvider {

    private final JavaMailSender mailSender;

    public EmailProvider(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendNotification(Notification notification) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("no-reply@notificationhub.local");
        message.setTo(notification.getRecipient());
        message.setSubject("Notificação - Notification Hub");
        message.setText(notification.getMessage());

        mailSender.send(message);
    }


}
