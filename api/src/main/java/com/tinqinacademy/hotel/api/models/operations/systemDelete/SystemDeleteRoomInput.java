package com.tinqinacademy.hotel.api.models.operations.systemDelete;

import com.tinqinacademy.hotel.api.models.base.OperationInput;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SystemDeleteRoomInput implements OperationInput {
    private String id;
}
