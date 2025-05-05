package com.pragma.notification_service.domain.usecase;

import com.pragma.notification_service.domain.api.INotificationServicePort;
import com.pragma.notification_service.domain.spi.INotificationPersistencePort;
import com.pragma.notification_service.domain.util.constants.NotificationUseCaseConstants;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@RequiredArgsConstructor
public class NotificationUseCase implements INotificationServicePort {

    private final INotificationPersistencePort notificationPersistencePort;
    private final Random random;

    @Override
    public void sendConfirmationCode(Long idOrder, String phoneNumber) {
        if(!phoneNumber.startsWith(NotificationUseCaseConstants.PLUS + NotificationUseCaseConstants.INDICATIVE)) {
            if(phoneNumber.startsWith(NotificationUseCaseConstants.INDICATIVE)) {
                phoneNumber = NotificationUseCaseConstants.PLUS + phoneNumber;
            }
            else {
                phoneNumber = NotificationUseCaseConstants.PLUS + NotificationUseCaseConstants.INDICATIVE + phoneNumber;
            }
        }
        String code = String.valueOf(generateConfirmationCode());
        String message  =String.format(
                NotificationUseCaseConstants.CONFIRMATION_CODE_MESSAGE,
                code
        );
        notificationPersistencePort.sendNotification(idOrder, phoneNumber, message, code);
    }

    @Override
    public boolean validateConfirmationCode(Long idOrder, String code) {
        String confirmationCode = notificationPersistencePort.getConfirmationCode(idOrder);
        if (confirmationCode == null) {
            return false;
        }
        return confirmationCode.equals(code);
    }

    private int generateConfirmationCode() {
        return NotificationUseCaseConstants.MIN_CONFIRMATION_CODE +
                random.nextInt(NotificationUseCaseConstants.MAX_CONFIRMATION_CODE);
    }
}
