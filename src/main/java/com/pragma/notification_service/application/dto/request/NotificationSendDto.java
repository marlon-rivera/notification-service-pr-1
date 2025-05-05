package com.pragma.notification_service.application.dto.request;

import com.pragma.notification_service.application.dto.utils.constants.NotificationSendDtoConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationSendDto {

    @Schema(
            description = NotificationSendDtoConstants.ID_ORDER_DESCRIPTION,
            example = NotificationSendDtoConstants.ID_ORDER_EXAMPLE
    )
    @NotNull(message = NotificationSendDtoConstants.ID_ORDER_NOT_NULL)
    private Long idOrder;
    @Schema(
            description = NotificationSendDtoConstants.PHONE_NUMBER_DESCRIPTION,
            example = NotificationSendDtoConstants.PHONE_NUMBER_EXAMPLE
    )
    @NotNull(message = NotificationSendDtoConstants.PHONE_NUMBER_NOT_NULL)
    private String phoneNumber;

}
