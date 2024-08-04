package com.tinqinacademy.hotel.api.models.exceptions.customExceptions;

import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class ReservationIsPresent extends Errors {
    public ReservationIsPresent(HttpStatus status, String message, Integer statusCode) {
        super(status, message, statusCode);
    }
}
