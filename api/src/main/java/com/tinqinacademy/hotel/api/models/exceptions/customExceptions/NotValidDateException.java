package com.tinqinacademy.hotel.api.models.exceptions.customExceptions;

public class NotValidDateException extends RuntimeException {
    public NotValidDateException(String message) {
        super(message);
    }

}
