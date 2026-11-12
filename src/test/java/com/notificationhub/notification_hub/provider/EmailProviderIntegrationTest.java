package com.notificationhub.notification_hub.provider;

import com.notificationhub.notification_hub.enumeracao.NotificationChannel;
import com.notificationhub.notification_hub.model.Notification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailProviderIntegrationTest {


    private final EmailProvider emailProvider;

    @Autowired
    public EmailProviderIntegrationTest(EmailProvider emailProvider) {
        this.emailProvider = emailProvider;
    }

    @Test
    public void shouldSendEmailToMailpit() {

        Notification notification = new Notification(
                "integracao@test.com",
                "Teste de integração MailPit",
                NotificationChannel.EMAIL
        );

        emailProvider.sendNotification(notification);

    }
}
