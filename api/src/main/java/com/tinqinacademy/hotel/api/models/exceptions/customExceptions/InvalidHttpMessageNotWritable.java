package com.tinqinacademy.hotel.api.models.exceptions.customExceptions;

import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class InvalidHttpMessageNotWritable extends Errors {
    public InvalidHttpMessageNotWritable(HttpStatus status, String message, Integer statusCode) {
        super(status, message, statusCode);
    }
}
