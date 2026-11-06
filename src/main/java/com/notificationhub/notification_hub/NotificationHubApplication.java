package com.notificationhub.notification_hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class NotificationHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationHubApplication.class, args);
	}

}
