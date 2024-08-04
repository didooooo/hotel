package com.tinqinacademy.hotel.api.models.operations.registerVisitor;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DataForVisitor {
    @NotBlank
    private String firstName;
    @NotNull
    private String lastName;
    @NotBlank
    private String phoneNo;
    private LocalDate birthdate;
    @NotBlank
    private String IDCardNumber;
    @FutureOrPresent
    private LocalDate validity;
    @NotBlank
    private String authority;
    @PastOrPresent(message = "error date")
    private LocalDate issueDate;
}
