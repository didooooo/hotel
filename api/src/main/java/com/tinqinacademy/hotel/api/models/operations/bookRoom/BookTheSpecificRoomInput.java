package com.tinqinacademy.hotel.api.models.operations.bookRoom;

import com.tinqinacademy.hotel.api.models.base.OperationInput;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BookTheSpecificRoomInput implements OperationInput {
    @Hidden
    private String roomId;
    private LocalDate startDate;
    @FutureOrPresent
    private LocalDate endDate;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String phoneNo;
    @Past
    private LocalDate birthdate;
    @Email
    private String email;
}
