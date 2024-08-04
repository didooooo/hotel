package com.tinqinacademy.hotel.api.models.operations.registerVisitor;

import com.tinqinacademy.hotel.api.models.base.OperationInput;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RegisterVisitorInput implements OperationInput {
    @FutureOrPresent
    private LocalDate startDate;
    @FutureOrPresent(message = "can not be past")
    private LocalDate endDate;
    @NotBlank
    private String roomId;
    private List<@Valid DataForVisitor> dataForVisitors;
}
