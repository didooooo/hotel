package com.tinqinacademy.hotel.api.models.operations.checkRoom;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CheckRoomAvailabilityOutput implements OperationOutput {
    private List<String> ids;
}
