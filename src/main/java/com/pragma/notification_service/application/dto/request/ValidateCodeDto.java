package com.pragma.notification_service.application.dto.request;

import com.pragma.notification_service.application.dto.utils.constants.ValidateCodeDtoConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidateCodeDto {

    @Schema(
            description = ValidateCodeDtoConstants.ID_ORDER_DESCRIPTION,
            example = ValidateCodeDtoConstants.ID_ORDER_EXAMPLE
    )
    @NotNull(message = ValidateCodeDtoConstants.ID_ORDER_NULL_MESSAGE)
    private Long idOrder;
    @Schema(
            description = ValidateCodeDtoConstants.CODE_DESCRIPTION,
            example = ValidateCodeDtoConstants.CODE_EXAMPLE
    )
    @NotBlank(message = ValidateCodeDtoConstants.CODE_NULL_MESSAGE)
    private String code;

}
