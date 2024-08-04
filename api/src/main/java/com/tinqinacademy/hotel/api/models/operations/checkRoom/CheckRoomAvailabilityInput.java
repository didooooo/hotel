package com.tinqinacademy.hotel.api.models.operations.checkRoom;

import com.tinqinacademy.hotel.api.models.base.OperationInput;
import com.tinqinacademy.hotel.api.models.enums.BathroomType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CheckRoomAvailabilityInput implements OperationInput {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> beds;
    private BathroomType bathroomType;
}
