package com.notificationhub.notification_hub.service;

import ch.qos.logback.core.testUtil.MockInitialContext;
import com.notificationhub.notification_hub.dto.NotificationRequestDTO;
import com.notificationhub.notification_hub.enumeracao.NotificationChannel;
import com.notificationhub.notification_hub.enumeracao.NotificationStatus;
import com.notificationhub.notification_hub.exception.NotificationNotFoundException;
import com.notificationhub.notification_hub.model.Notification;
import com.notificationhub.notification_hub.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NotificationServiceTest {

    private final NotificationRepository notificationRepository =
            Mockito.mock(NotificationRepository.class);

    private final NotificationService notificationService =
            new NotificationService(notificationRepository);

    @Test
    void deveLancarExcecaoQuandoNotificacaoNaoExiste() {
        Mockito.when(notificationRepository.findById(999L))
                .thenReturn(null);

        assertThrows(
                NotificationNotFoundException.class,
                () -> {
                    notificationService.findById(999L);
                }
        );
    }

    @Test
    void deveRetornarNotificacaoQuandoIdExiste() {
        Notification notification = new Notification(
                "joao@teste.com",
                "Ola mundo",
                NotificationChannel.EMAIL);

        notification.setId(1L);

        Mockito.when(notificationRepository.findById(1L))
                .thenReturn(notification);

        Notification resultado = notificationService.findById(1L);


        assertEquals(1L, resultado.getId());
    }

    @Test
    void deveCriarNotificacaoComStatusPending() {
        NotificationRequestDTO notificationRequestDTO=  new NotificationRequestDTO(
                "joao@teste.com",
                "Mensagem de teste",
                NotificationChannel.EMAIL
        );

        Mockito.when(notificationRepository.save(Mockito.any(Notification.class)))
                .thenAnswer(invocation -> {
                    Notification notification = invocation.getArgument(0);
                    notification.setId(1L);
                    return notification;
                });

        Notification resultado = notificationService.create(notificationRequestDTO);

        assertEquals(NotificationStatus.PENDING, resultado.getStatus());
        assertEquals(1L, resultado.getId());
    }
}
