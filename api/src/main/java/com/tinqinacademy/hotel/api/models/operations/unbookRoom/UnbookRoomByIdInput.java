package com.tinqinacademy.hotel.api.models.operations.unbookRoom;

import com.tinqinacademy.hotel.api.models.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UnbookRoomByIdInput  implements OperationInput {
    @NotBlank
    private String bookingId;
}
