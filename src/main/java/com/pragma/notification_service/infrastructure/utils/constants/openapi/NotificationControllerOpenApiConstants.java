package com.pragma.notification_service.infrastructure.utils.constants.openapi;

public class NotificationControllerOpenApiConstants {

    private NotificationControllerOpenApiConstants(){
        // Prevent instantiation
    }

    public static final String SEND_CONFIRMATION_CODE_SUMMARY = "Send confirmation code";
    public static final String SEND_CONFIRMATION_CODE_DESCRIPTION = "Send a confirmation code to the user's phone number";
    public static final String SEND_CONFIRMATION_CODE_RESPONSE_200_DESCRIPTION = "Confirmation code sent successfully";
    public static final String SEND_CONFIRMATION_CODE_RESPONSE_400_DESCRIPTION = "Invalid request data";
    public static final String VALIDATE_CONFIRMATION_CODE_SUMMARY = "Validate confirmation code";
    public static final String VALIDATE_CONFIRMATION_CODE_DESCRIPTION = "Validate the confirmation code sent to the user's phone number";
    public static final String VALIDATE_CONFIRMATION_CODE_RESPONSE_200_DESCRIPTION = "Confirmation code validated successfully";
    public static final String VALIDATE_CONFIRMATION_CODE_RESPONSE_400_DESCRIPTION = "Invalid request data";
    public static final String CANCEL_CONFIRMATION_CODE_SUMMARY = "Send message of cancel is not possible";
    public static final String CANCEL_CONFIRMATION_CODE_DESCRIPTION = "Send message of cancel is not possible";
    public static final String CANCEL_CONFIRMATION_CODE_RESPONSE_200_DESCRIPTION = "Message sent successfully";
    public static final String CANCEL_CONFIRMATION_CODE_RESPONSE_400_DESCRIPTION = "Invalid request data";

}
