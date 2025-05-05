package com.pragma.notification_service.application.handler;

import com.pragma.notification_service.application.dto.request.NotificationSendDto;

public interface INotificationHandler {

    void sendConfirmationCode(NotificationSendDto notificationSendDto);
    boolean validateConfirmationCode(Long idOrder, String code);

}
