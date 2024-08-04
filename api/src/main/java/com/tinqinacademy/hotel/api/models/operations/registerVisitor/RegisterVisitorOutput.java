package com.tinqinacademy.hotel.api.models.operations.registerVisitor;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RegisterVisitorOutput implements OperationOutput {
    private String message;
}
