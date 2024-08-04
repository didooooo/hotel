package com.tinqinacademy.hotel.api.models.operations.systemDelete;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SystemDeleteRoomOutput implements OperationOutput {
    private String message;
}
