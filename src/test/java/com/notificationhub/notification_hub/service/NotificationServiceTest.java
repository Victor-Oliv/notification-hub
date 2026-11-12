package com.notificationhub.notification_hub.service;

import com.notificationhub.notification_hub.dto.NotificationRequestDTO;
import com.notificationhub.notification_hub.enumeracao.NotificationChannel;
import com.notificationhub.notification_hub.enumeracao.NotificationStatus;
import com.notificationhub.notification_hub.exception.NotificationNotFoundException;
import com.notificationhub.notification_hub.model.Notification;
import com.notificationhub.notification_hub.provider.NotificationProvider;
import com.notificationhub.notification_hub.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationProvider notificationProvider;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void deveLancarExcecaoQuandoNotificacaoNaoExiste() {
        Mockito.when(notificationRepository.findById(999L))
                .thenReturn(Optional.empty());

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
                .thenReturn(Optional.of(notification));

        Notification resultado = notificationService.findById(1L);


        assertEquals(1L, resultado.getId());
    }

    @Test
    void deveCriarNotificacaoComStatusSent() {
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

        assertEquals(NotificationStatus.SENT, resultado.getStatus());
        assertEquals(1L, resultado.getId());

        verify(notificationProvider, times(1)).sendNotification(Mockito.any(Notification.class));
    }

    @Test
    void shouldRejectUnsupportedNotificationChannel(){
        NotificationRequestDTO notification = new NotificationRequestDTO(
                "joao@teste.com",
                "Ola mundo",
                NotificationChannel.SMS);

        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    notificationService.create(notification);
                }
        );

        verifyNoInteractions(notificationRepository);
        verifyNoInteractions(notificationProvider);
    }

    @Test
    void shouldMarkNotificationAsFailedWhenEmailSendingFails() {

        NotificationRequestDTO notification = new NotificationRequestDTO(
                "joao@teste.com",
                "Ola mundo",
                NotificationChannel.EMAIL);


        doThrow(new MailSendException("SMTP indisponível"))
                .when(notificationProvider)
                .sendNotification(any(Notification.class));

        Notification result = notificationService.create(notification);

        assertEquals(NotificationStatus.FAILED, result.getStatus());

        verify(notificationRepository, times(2)).save(Mockito.any(Notification.class));

    }
}
