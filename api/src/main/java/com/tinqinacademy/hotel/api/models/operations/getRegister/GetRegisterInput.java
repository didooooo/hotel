package com.tinqinacademy.hotel.api.models.operations.getRegister;

import com.tinqinacademy.hotel.api.models.base.OperationInput;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GetRegisterInput  implements OperationInput {
    private LocalDate startDate;
    private LocalDate endDate;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String IDCardNumber;
    private LocalDate validity;
    private String authority;
    private LocalDate date;
    private LocalDate birthdate;
    private Integer roomNo;
}
