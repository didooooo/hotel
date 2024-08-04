package com.tinqinacademy.hotel.api.models.operations.systemPatch;

import com.tinqinacademy.hotel.api.models.base.OperationInput;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SystemPatchRoomInput  implements OperationInput {
    @Hidden
    private String id;
    private List<String> beds;
    @NotBlank
    private String bathroomType;
    private String roomNumber;
    private BigDecimal price;
}
