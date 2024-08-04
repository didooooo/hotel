package com.tinqinacademy.hotel.api.models.operations.unbookRoom;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UnbookRoomByIdOutput implements OperationOutput {
    private String message;
}
