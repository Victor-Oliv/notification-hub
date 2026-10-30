package com.notificationhub.notification_hub.dto;

import com.notificationhub.notification_hub.enumeracao.NotificationChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificationRequestDTO(

        @NotBlank
        String recipient,

        @NotBlank
        String message,

        @NotNull
        NotificationChannel channel
) {
}
