package com.notificationhub.notification_hub.provider;

import com.notificationhub.notification_hub.enumeracao.NotificationChannel;
import com.notificationhub.notification_hub.model.Notification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class EmailProviderTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailProvider emailProvider;


    @Test
    void shouldSendEmailNotification() {

        Notification notification = new Notification(
                "cliente@teste.com",
                "Seu pedido foi aprovado!",
                NotificationChannel.EMAIL
        );

        emailProvider.sendNotification(notification);

        ArgumentCaptor<SimpleMailMessage> captor =
                ArgumentCaptor.forClass(SimpleMailMessage.class);

        verify(mailSender).send(captor.capture());

        SimpleMailMessage capturedMessage = captor.getValue();

        assertEquals(
                "cliente@teste.com",
                capturedMessage.getTo()[0]
        );

        assertEquals(
                "Seu pedido foi aprovado!",
                capturedMessage.getText()
        );
    }

    @Test
    void shouldThrowExceptionWhenEmailSendingFails() {

        Notification notification = new Notification(
                "cliente@teste.com",
                "Mensagem de teste",
                NotificationChannel.EMAIL
        );

        doThrow(new MailSendException("Falha no SMTP"))
                .when(mailSender)
                .send(any(SimpleMailMessage.class));

        assertThrows(
                MailSendException.class,
                () -> emailProvider.sendNotification(notification)
        );

    }
}
