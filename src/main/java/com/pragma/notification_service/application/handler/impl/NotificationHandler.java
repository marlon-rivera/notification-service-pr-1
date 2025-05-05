package com.pragma.notification_service.application.handler.impl;

import com.pragma.notification_service.application.dto.request.NotificationSendDto;
import com.pragma.notification_service.application.handler.INotificationHandler;
import com.pragma.notification_service.domain.api.INotificationServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationHandler implements INotificationHandler {

    private final INotificationServicePort notificationServicePort;

    @Override
    public void sendConfirmationCode(NotificationSendDto notificationSendDto) {
        notificationServicePort.sendConfirmationCode(
                notificationSendDto.getIdOrder(),
                notificationSendDto.getPhoneNumber()
        );
    }

    @Override
    public boolean validateConfirmationCode(Long idOrder, String code) {
        return notificationServicePort.validateConfirmationCode(idOrder, code);
    }
}
