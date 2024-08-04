package com.tinqinacademy.hotel.api.models.operations.getRoomById;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import com.tinqinacademy.hotel.api.models.enums.BathroomType;
import com.tinqinacademy.hotel.api.models.enums.Bed;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GetRoomByIDOutput implements OperationOutput {
    private String id;
    private BigDecimal price;
    private Integer floor;
    private List<Bed> beds;
    private BathroomType bathroomType;
    private List<LocalDate> datesOccupied;

}
