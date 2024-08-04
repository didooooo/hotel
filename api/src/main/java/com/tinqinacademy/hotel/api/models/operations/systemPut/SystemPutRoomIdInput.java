package com.tinqinacademy.hotel.api.models.operations.systemPut;

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
@Setter()
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SystemPutRoomIdInput implements OperationInput {
    @Hidden
    private UUID roomId;
    private List<String> beds;
    @NotBlank
    private String bathroomType;
    @Positive
    private Integer floor;
    @NotBlank
    private String roomNo;
    @Positive
    private BigDecimal price;
}
