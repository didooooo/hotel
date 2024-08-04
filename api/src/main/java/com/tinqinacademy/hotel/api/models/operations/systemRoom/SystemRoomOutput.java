package com.tinqinacademy.hotel.api.models.operations.systemRoom;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SystemRoomOutput implements OperationOutput {
    private UUID id;
}
