package com.pragma.notification_service.infrastructure.out.jpa.adapter;

import com.pragma.notification_service.domain.spi.INotificationPersistencePort;
import com.pragma.notification_service.infrastructure.out.jpa.entity.NotificationCodeEntity;
import com.pragma.notification_service.infrastructure.out.jpa.repository.INotificationCodeRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public class NotificationAdapterJPA implements INotificationPersistencePort {

    private final INotificationCodeRepository notificationCodeRepository;
    @Value("${twilio.phone-number}")
    private String phoneNumberTwilio;

    @Override
    public void sendNotification(Long idOrder, String phoneNumber, String message, String code) {
        notificationCodeRepository.save(
                new NotificationCodeEntity(null, idOrder, phoneNumber, code)
        );
        Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(phoneNumberTwilio),
                message
        ).create();
    }
    @Override
    public String getConfirmationCode(Long idOrder) {
        return notificationCodeRepository.findByIdOrder(idOrder).getConfirmationCode();
    }
}
