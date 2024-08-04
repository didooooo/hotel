package com.tinqinacademy.hotel.api.models.exceptions.customExceptions;

import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class ReservationNotFound extends Errors {
    public ReservationNotFound(HttpStatus status, String message, Integer statusCode) {
        super(status, message, statusCode);
    }
}
