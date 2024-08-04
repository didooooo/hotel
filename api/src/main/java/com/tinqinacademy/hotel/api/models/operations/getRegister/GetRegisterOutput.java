package com.tinqinacademy.hotel.api.models.operations.getRegister;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
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
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String IDCardNumber;
    private LocalDate validity;
    private String authority;
    private LocalDate date;
}
