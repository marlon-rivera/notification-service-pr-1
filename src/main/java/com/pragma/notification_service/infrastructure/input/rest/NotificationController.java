package com.pragma.notification_service.infrastructure.input.rest;

import com.pragma.notification_service.application.dto.request.NotificationSendDto;
import com.pragma.notification_service.application.dto.request.ValidateCodeDto;
import com.pragma.notification_service.application.dto.utils.constants.ResponsesCodes;
import com.pragma.notification_service.application.handler.INotificationHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pragma.notification_service.infrastructure.utils.constants.openapi.NotificationControllerOpenApiConstants;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final INotificationHandler notificationHandler;

    @Operation(
            summary = NotificationControllerOpenApiConstants.SEND_CONFIRMATION_CODE_SUMMARY,
            description = NotificationControllerOpenApiConstants.SEND_CONFIRMATION_CODE_DESCRIPTION,
            responses = {
                    @ApiResponse(
                            responseCode = ResponsesCodes.OK,
                            description = NotificationControllerOpenApiConstants.SEND_CONFIRMATION_CODE_RESPONSE_200_DESCRIPTION
                    ),
                    @ApiResponse(
                            responseCode = ResponsesCodes.BAD_REQUEST,
                            description = NotificationControllerOpenApiConstants.SEND_CONFIRMATION_CODE_RESPONSE_400_DESCRIPTION
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Void> sendConfirmationCode(@Valid @RequestBody NotificationSendDto notificationSendDto) {
        notificationHandler.sendConfirmationCode(notificationSendDto);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = NotificationControllerOpenApiConstants.VALIDATE_CONFIRMATION_CODE_SUMMARY,
            description = NotificationControllerOpenApiConstants.VALIDATE_CONFIRMATION_CODE_DESCRIPTION,
            responses = {
                    @ApiResponse(
                            responseCode = ResponsesCodes.OK,
                            description = NotificationControllerOpenApiConstants.VALIDATE_CONFIRMATION_CODE_RESPONSE_200_DESCRIPTION
                    ),
                    @ApiResponse(
                            responseCode = ResponsesCodes.BAD_REQUEST,
                            description = NotificationControllerOpenApiConstants.VALIDATE_CONFIRMATION_CODE_RESPONSE_400_DESCRIPTION
                    )
            }
    )
    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateConfirmationCode(
            @RequestBody @Valid ValidateCodeDto validateCodeDto
    ) {
        return ResponseEntity.ok(notificationHandler.validateConfirmationCode(
                validateCodeDto.getIdOrder(),
                validateCodeDto.getCode()
        ));
    }

    @Operation(
            summary = NotificationControllerOpenApiConstants.CANCEL_CONFIRMATION_CODE_SUMMARY,
            description = NotificationControllerOpenApiConstants.CANCEL_CONFIRMATION_CODE_DESCRIPTION,
            responses = {
                    @ApiResponse(
                            responseCode = ResponsesCodes.OK,
                            description = NotificationControllerOpenApiConstants.CANCEL_CONFIRMATION_CODE_RESPONSE_200_DESCRIPTION
                    ),
                    @ApiResponse(
                            responseCode = ResponsesCodes.BAD_REQUEST,
                            description = NotificationControllerOpenApiConstants.CANCEL_CONFIRMATION_CODE_RESPONSE_400_DESCRIPTION
                    )
            }
    )
    @PostMapping("/cancel/{phoneNumber}")
    public ResponseEntity<Void> sendNotificationCancel(
            @PathVariable String phoneNumber
    ) {
        notificationHandler.sendNotificationCancelOrder(phoneNumber);
        return ResponseEntity.ok().build();
    }
}
