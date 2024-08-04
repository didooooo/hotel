package com.tinqinacademy.hotel.api.models.operations.bookRoom;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BookTheSpecificRoomOutput implements OperationOutput {
    private String message;

}
