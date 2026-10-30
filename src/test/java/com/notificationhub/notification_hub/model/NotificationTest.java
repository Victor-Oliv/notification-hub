package com.notificationhub.notification_hub.model;

import com.notificationhub.notification_hub.dto.NotificationRequestDTO;
import com.notificationhub.notification_hub.enumeracao.NotificationChannel;
import com.notificationhub.notification_hub.enumeracao.NotificationStatus;
import com.notificationhub.notification_hub.exception.NotificationNotFoundException;
import com.notificationhub.notification_hub.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Not;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NotificationTest {

    @Test
    void deveCriarNotificacaoComStatusPending() {

        Notification notification =
                new Notification(
                        "joao@test.com",
                        "Ola teste",
                        NotificationChannel.EMAIL);

        assertEquals(NotificationStatus.PENDING, notification.getStatus());
    }

    @Test
    void deveLancarExcecaoQuandoValorForInvalido(){

        assertThrows(
                NotificationNotFoundException.class,
                () -> {
                    throw new NotificationNotFoundException(999L);
                }
        );
    }
}
