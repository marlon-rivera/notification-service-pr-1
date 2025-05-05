package com.pragma.notification_service.infrastructure.out.jpa.adapter;

import com.pragma.notification_service.infrastructure.out.jpa.entity.NotificationCodeEntity;
import com.pragma.notification_service.infrastructure.out.jpa.repository.INotificationCodeRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationAdapterJPATest {

    @Mock
    private INotificationCodeRepository notificationCodeRepository;

    @Mock
    private MessageCreator messageCreator;

    @InjectMocks
    private NotificationAdapterJPA notificationAdapterJPA;

    private final String twilioPhoneNumber = "+12345678901";
    private final Long orderId = 1L;
    private final String customerPhoneNumber = "+573507310045";
    private final String message = "Your verification code is: ";
    private final String code = "123456";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(notificationAdapterJPA, "phoneNumberTwilio", twilioPhoneNumber);
    }

    @Test
    void sendNotification_shouldSaveNotificationAndSendSms() {
        // Arrange
        ArgumentCaptor<NotificationCodeEntity> entityCaptor = ArgumentCaptor.forClass(NotificationCodeEntity.class);

        try (MockedStatic<Message> mockedMessage = mockStatic(Message.class)) {
            mockedMessage.when(() -> Message.creator(
                    any(PhoneNumber.class),
                    any(PhoneNumber.class),
                    anyString())
            ).thenReturn(messageCreator);
            when(messageCreator.create()).thenReturn(null);

            // Act
            notificationAdapterJPA.sendNotification(orderId, customerPhoneNumber, message + code, code);

            // Assert
            verify(notificationCodeRepository).save(entityCaptor.capture());
            NotificationCodeEntity savedEntity = entityCaptor.getValue();
            assertEquals(orderId, savedEntity.getIdOrder());
            assertEquals(customerPhoneNumber, savedEntity.getPhoneNumber());
            assertEquals(code, savedEntity.getConfirmationCode());

            mockedMessage.verify(() -> Message.creator(
                    eq(new PhoneNumber(customerPhoneNumber)),
                    eq(new PhoneNumber(twilioPhoneNumber)),
                    eq(message + code)
            ));
            verify(messageCreator).create();
        }
    }

    @Test
    void getConfirmationCode_shouldReturnCodeFromRepository() {
        // Arrange
        NotificationCodeEntity notificationCodeEntity = new NotificationCodeEntity();
        notificationCodeEntity.setConfirmationCode(code);
        when(notificationCodeRepository.findByIdOrder(orderId)).thenReturn(notificationCodeEntity);

        // Act
        String result = notificationAdapterJPA.getConfirmationCode(orderId);

        // Assert
        verify(notificationCodeRepository).findByIdOrder(orderId);
        assertEquals(code, result);
    }

    @Test
    void sendNotificationCancelOrder_shouldSendSms() {
        // Arrange
        ArgumentCaptor<NotificationCodeEntity> entityCaptor = ArgumentCaptor.forClass(NotificationCodeEntity.class);

        try (MockedStatic<Message> mockedMessage = mockStatic(Message.class)) {
            mockedMessage.when(() -> Message.creator(
                    any(PhoneNumber.class),
                    any(PhoneNumber.class),
                    anyString())
            ).thenReturn(messageCreator);
            when(messageCreator.create()).thenReturn(null);

            // Act
            notificationAdapterJPA.sendNotificationCancelOrder(customerPhoneNumber, message);

            // Assert
            mockedMessage.verify(() -> Message.creator(
                    eq(new PhoneNumber(customerPhoneNumber)),
                    eq(new PhoneNumber(twilioPhoneNumber)),
                    eq(message)
            ));
            verify(messageCreator).create();
        }
    }

}