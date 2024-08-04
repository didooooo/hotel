package com.tinqinacademy.hotel.api.models.exceptions;

import com.tinqinacademy.hotel.api.models.exceptions.customExceptions.BlankStringException;
import org.springframework.stereotype.Component;


@Component
public class ErrorMapper {
    private final ErrorMapings errorMapings;

    public ErrorMapper(ErrorMapings errorMapings) {
        this.errorMapings = errorMapings;
    }

    public <T extends Throwable> Errors mapError(T exception) {
        Errors errors = errorMapings.getExceptionToError().get(exception.getClass());
        return errors;
    }



}
