package com.tinqinacademy.hotel.api.models.operations.systemPut;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SystemPutRoomOutput implements OperationOutput {
    private String id;
}
