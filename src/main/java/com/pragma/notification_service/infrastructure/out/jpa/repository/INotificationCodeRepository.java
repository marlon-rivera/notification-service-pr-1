package com.pragma.notification_service.infrastructure.out.jpa.repository;

import com.pragma.notification_service.infrastructure.out.jpa.entity.NotificationCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INotificationCodeRepository extends JpaRepository<NotificationCodeEntity, Long> {

    NotificationCodeEntity findByIdOrder(Long idOrder);
}
