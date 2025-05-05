package com.pragma.notification_service.infrastructure.configuration;

import com.pragma.notification_service.application.handler.INotificationHandler;
import com.pragma.notification_service.application.handler.impl.NotificationHandler;
import com.pragma.notification_service.domain.api.INotificationServicePort;
import com.pragma.notification_service.domain.spi.INotificationPersistencePort;
import com.pragma.notification_service.domain.usecase.NotificationUseCase;
import com.pragma.notification_service.infrastructure.out.jpa.adapter.NotificationAdapterJPA;
import com.pragma.notification_service.infrastructure.out.jpa.repository.INotificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final INotificationCodeRepository notificationCodeRepository;

    @Bean
    public INotificationPersistencePort notificationPersistencePort() {
        return new NotificationAdapterJPA(notificationCodeRepository);
    }

    @Bean
    public INotificationServicePort notificationServicePort() {
        return new NotificationUseCase(notificationPersistencePort(), new Random());
    }

    @Bean
    public INotificationHandler notificationHandler() {
        return new NotificationHandler(notificationServicePort());
    }



}
