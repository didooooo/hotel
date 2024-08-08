package com.tinqinacademy.hotel.api.models.operations.getRegister;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.DataForVisitor;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GetRegisterOutput implements OperationOutput {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<DataForVisitor> data;
}
