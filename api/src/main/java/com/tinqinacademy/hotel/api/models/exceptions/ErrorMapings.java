package com.tinqinacademy.hotel.api.models.exceptions;

import com.tinqinacademy.hotel.api.models.exceptions.customExceptions.*;
import lombok.Getter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Getter
@Component
public class ErrorMapings implements ApplicationRunner {
    public Map<Class<? extends Throwable>,Errors> exceptionToError;
    private void fillMap(){
       exceptionToError.put(BlankStringException.class, new BlankStringException(HttpStatus.BAD_REQUEST,"Blank String",HttpStatus.BAD_REQUEST.value()));
       exceptionToError.put(HttpMessageNotWritableException.class, new InvalidHttpMessageNotWritable(HttpStatus.BAD_REQUEST,"Http Message Not Writable Exception",HttpStatus.BAD_REQUEST.value()));
       exceptionToError.put(RoomNotFoundException.class, new RoomNotFoundException(HttpStatus.BAD_REQUEST,"Room not exist",HttpStatus.BAD_REQUEST.value()));
       exceptionToError.put(StartDateAfterEndDateException.class, new StartDateAfterEndDateException(HttpStatus.BAD_REQUEST,"Start date is after end date",HttpStatus.BAD_REQUEST.value()));
       exceptionToError.put(ReservationAlreadyExists.class, new StartDateAfterEndDateException(HttpStatus.BAD_REQUEST,"Reservation already exists",HttpStatus.BAD_REQUEST.value()));
       exceptionToError.put(ReservationNotFound.class, new ReservationNotFound(HttpStatus.BAD_REQUEST,"Reservation not found",HttpStatus.BAD_REQUEST.value()));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        exceptionToError = new HashMap<>();
        fillMap();
    }
}
