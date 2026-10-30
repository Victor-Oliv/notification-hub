package com.notificationhub.notification_hub.model;

import com.notificationhub.notification_hub.enumeracao.NotificationChannel;
import com.notificationhub.notification_hub.enumeracao.NotificationStatus;

import java.util.Objects;

public class Notification {

    private Long id;

    private String recipient;

    private String message;

    private NotificationChannel channel;

    private NotificationStatus status;

    public Notification(String recipient, String message, NotificationChannel channel){
        this.recipient = recipient;
        this.message = message;
        this.channel = channel;
        this.status = NotificationStatus.PENDING;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", recipient='" + recipient + '\'' +
                ", message='" + message + '\'' +
                ", channel=" + channel +
                ", status=" + status +
                '}';
    }
}
