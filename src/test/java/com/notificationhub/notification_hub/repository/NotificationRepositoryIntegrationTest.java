package com.notificationhub.notification_hub.repository;

import com.notificationhub.notification_hub.enumeracao.NotificationChannel;
import com.notificationhub.notification_hub.model.Notification;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.springframework.test.context.transaction.TestTransaction;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class NotificationRepositoryIntegrationTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EntityManager entityManager;

    @ServiceConnection
    @Container
    static PostgreSQLContainer postgres =
            new PostgreSQLContainer("postgres:17");


    @Test
    void shouldSaveNotification() {

        Notification notification =
                new Notification(
                        "teste-integracao@email.com",
                        "Teste de persistência",
                        NotificationChannel.EMAIL
                );

        Notification notificationSaved = notificationRepository.save(notification);
        entityManager.flush();
        entityManager.clear();

        Assertions.assertNotNull(notificationSaved.getId());

        Optional<Notification> notificationGen = notificationRepository.findById(notificationSaved.getId());

        Assertions.assertTrue(notificationGen.isPresent());

        Assertions.assertEquals(notification.getRecipient(), notificationGen.get().getRecipient());
    }

    @Test
    void shouldRollbackNotification() {

        Notification notification =
                new Notification(
                        "commit@email.com",
                        "Notificação confirmada",
                        NotificationChannel.EMAIL
                );

        Notification notificationGen = notificationRepository.save(notification);
        TestTransaction.flagForCommit();
        TestTransaction.end();

        TestTransaction.start();

        Notification notificationRollback =
                new Notification(
                        "rollback@email.com",
                        "Notificação que será desfeita",
                        NotificationChannel.SMS
                );

        Notification notificationRollbackSaved = notificationRepository.save(notificationRollback);
        TestTransaction.flagForRollback();
        TestTransaction.end();

        Assertions.assertTrue(
                notificationRepository.existsById(notificationGen.getId())
        );

        Assertions.assertFalse(
                notificationRepository.existsById(notificationRollbackSaved.getId())
        );
    }

}
