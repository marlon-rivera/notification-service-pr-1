package com.pragma.notification_service.usecase;

import com.pragma.notification_service.domain.spi.INotificationPersistencePort;
import com.pragma.notification_service.domain.usecase.NotificationUseCase;
import com.pragma.notification_service.domain.util.constants.NotificationUseCaseConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationUseCaseTest {

    @Mock
    private INotificationPersistencePort notificationPersistencePort;

    @Mock
    private Random randomMock;

    @InjectMocks
    private NotificationUseCase notificationUseCase;

    @BeforeEach
    void setUp() {
        // This method is called before each test to set up the necessary mocks and inject them into the NotificationUseCase instance.
        // The @InjectMocks annotation automatically injects the mocks into the NotificationUseCase instance.
    }

    @Test
    void shouldSendConfirmationCodeWithCorrectFormat() {
        // Given
        Long idOrder = 1L;
        String phoneNumber = "+573001234567";
        int mockCode = 123456;

        when(randomMock.nextInt(anyInt())).thenReturn(mockCode - NotificationUseCaseConstants.MIN_CONFIRMATION_CODE);

        // When
        notificationUseCase.sendConfirmationCode(idOrder, phoneNumber);

        // Then
        String expectedMessage = String.format(NotificationUseCaseConstants.CONFIRMATION_CODE_MESSAGE, mockCode);
        verify(notificationPersistencePort).sendNotification(
                eq(idOrder),
                eq(phoneNumber),
                eq(expectedMessage),
                eq(String.valueOf(mockCode))
        );
    }

    @Test
    void shouldSendConfirmationCodeWithCorrectFormatWhenPhoneNumberWithoutPlus() {
        // Given
        Long idOrder = 1L;
        String phoneNumber = "573001234567";
        int mockCode = 123456;
        when(randomMock.nextInt(anyInt())).thenReturn(mockCode - NotificationUseCaseConstants.MIN_CONFIRMATION_CODE);

        // When
        notificationUseCase.sendConfirmationCode(idOrder, phoneNumber);

        // Then
        verify(notificationPersistencePort).sendNotification(
                eq(idOrder),
                eq("+" + phoneNumber),
                anyString(),
                anyString()
        );
    }

    @Test
    void shouldSendConfirmationCodeWithCorrectFormatWhenPhoneNumberWithoutIndicative() {
        // Given
        Long idOrder = 1L;
        String phoneNumber = "3001234567";
        int mockCode = 123456;
        when(randomMock.nextInt(anyInt())).thenReturn(mockCode - NotificationUseCaseConstants.MIN_CONFIRMATION_CODE);

        // When
        notificationUseCase.sendConfirmationCode(idOrder, phoneNumber);

        // Then
        verify(notificationPersistencePort).sendNotification(
                eq(idOrder),
                eq("+573001234567"),
                anyString(),
                anyString()
        );
    }


    @Test
    void shouldNotModifyPhoneNumberWhenAlreadyFormattedCorrectly() {
        // Given
        Long idOrder = 1L;
        String phoneNumber = "+573001234567";
        int mockCode = 123456;
        when(randomMock.nextInt(anyInt())).thenReturn(mockCode - NotificationUseCaseConstants.MIN_CONFIRMATION_CODE);

        // When
        notificationUseCase.sendConfirmationCode(idOrder, phoneNumber);

        // Then
        verify(notificationPersistencePort).sendNotification(
                eq(idOrder),
                eq(phoneNumber),
                anyString(),
                anyString()
        );
    }

    @Test
    void shouldGenerateConfirmationCodeInValidRange() {
        // Given
        Long idOrder = 1L;
        String phoneNumber = "+573001234567";
        int mockRandomValue = 9000;
        when(randomMock.nextInt(anyInt())).thenReturn(mockRandomValue);

        // When
        notificationUseCase.sendConfirmationCode(idOrder, phoneNumber);

        // Then
        int expectedCode = NotificationUseCaseConstants.MIN_CONFIRMATION_CODE + mockRandomValue;
        verify(notificationPersistencePort).sendNotification(
                anyLong(),
                anyString(),
                anyString(),
                eq(String.valueOf(expectedCode))
        );
    }

    @Test
    void shouldReturnTrueWhenCodeMatches() {
        // Given
        Long idOrder = 1L;
        String code = "123456";
        when(notificationPersistencePort.getConfirmationCode(idOrder)).thenReturn(code);

        // When
        boolean result = notificationUseCase.validateConfirmationCode(idOrder, code);

        // Then
        verify(notificationPersistencePort).getConfirmationCode(idOrder);
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenCodeDoesNotMatch() {
        // Given
        Long idOrder = 1L;
        String storedCode = "123456";
        String providedCode = "654321";
        when(notificationPersistencePort.getConfirmationCode(idOrder)).thenReturn(storedCode);

        // When
        boolean result = notificationUseCase.validateConfirmationCode(idOrder, providedCode);

        // Then
        verify(notificationPersistencePort).getConfirmationCode(idOrder);
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseWhenNoCodeFound() {
        // Given
        Long idOrder = 1L;
        String code = "123456";
        when(notificationPersistencePort.getConfirmationCode(idOrder)).thenReturn(null);

        // When
        boolean result = notificationUseCase.validateConfirmationCode(idOrder, code);

        // Then
        verify(notificationPersistencePort).getConfirmationCode(idOrder);
        assertFalse(result);
    }

    @Test
    void shouldSendCancelOrderNotificationWithCorrectMessage() {
        // Given
        String phoneNumber = "+573001234567";

        // When
        notificationUseCase.sendNotificationCancelOrder(phoneNumber);

        // Then
        verify(notificationPersistencePort).sendNotificationCancelOrder(
                eq(phoneNumber),
                eq(NotificationUseCaseConstants.MESSAGE_CANCEL_ORDER_NOTIFICATION)
        );
    }

    @Test
    void shouldFormatPhoneNumberCorrectlyWhenSendingCancelOrderNotification() {
        // Given
        String phoneNumber = "3001234567"; // Sin el prefijo +57

        // When
        notificationUseCase.sendNotificationCancelOrder(phoneNumber);

        // Then
        verify(notificationPersistencePort).sendNotificationCancelOrder(
                eq("+573001234567"),
                eq(NotificationUseCaseConstants.MESSAGE_CANCEL_ORDER_NOTIFICATION)
        );
    }

    @Test
    void shouldAddPlusSignWhenPhoneNumberStartsWithIndicative() {
        // Given
        String phoneNumber = "573001234567"; // Con indicativo pero sin +

        // When
        notificationUseCase.sendNotificationCancelOrder(phoneNumber);

        // Then
        verify(notificationPersistencePort).sendNotificationCancelOrder(
                eq("+573001234567"),
                eq(NotificationUseCaseConstants.MESSAGE_CANCEL_ORDER_NOTIFICATION)
        );
    }
}