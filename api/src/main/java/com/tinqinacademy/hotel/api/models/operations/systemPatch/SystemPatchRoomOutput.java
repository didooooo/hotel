package com.tinqinacademy.hotel.api.models.operations.systemPatch;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SystemPatchRoomOutput implements OperationOutput {
    private String id;
}
