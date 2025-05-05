package com.pragma.notification_service.domain.spi;

public interface INotificationPersistencePort {

    void sendNotification(Long idOrder, String phoneNumber, String message, String code);
    String getConfirmationCode(Long idOrder);
    void sendNotificationCancelOrder(String phoneNumber, String message);

}
