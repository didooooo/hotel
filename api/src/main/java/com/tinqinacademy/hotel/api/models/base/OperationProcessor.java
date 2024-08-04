package com.tinqinacademy.hotel.api.models.base;

import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import io.vavr.control.Either;

public interface OperationProcessor<O extends OperationOutput, I extends OperationInput> {
    Either<Errors, O> process(I input);
}
