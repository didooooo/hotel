package com.tinqinacademy.hotel.api.models.operations.getRoomById;

import com.tinqinacademy.hotel.api.models.base.OperationInput;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GetRoomByIDInput implements OperationInput {
    private String roomId;
}
