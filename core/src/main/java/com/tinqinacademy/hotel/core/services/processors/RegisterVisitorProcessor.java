package com.tinqinacademy.hotel.core.services.processors;

import com.tinqinacademy.hotel.api.models.exceptions.ErrorMapper;
import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.exceptions.customExceptions.ReservationAlreadyExists;
import com.tinqinacademy.hotel.api.models.exceptions.customExceptions.ReservationNotFound;
import com.tinqinacademy.hotel.api.models.exceptions.customExceptions.RoomNotFoundException;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.DataForVisitor;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorOperation;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.persistence.entity.Guest;
import com.tinqinacademy.hotel.persistence.entity.Reservation;
import com.tinqinacademy.hotel.persistence.entity.Room;
import com.tinqinacademy.hotel.persistence.repository.interfaces.GuestRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.ReservationRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterVisitorProcessor implements RegisterVisitorOperation {
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final ErrorMapper errorMapper;
    private final ConversionService conversionService;

    @Override
    public Either<Errors, RegisterVisitorOutput> process(RegisterVisitorInput input) {
        log.info("Start register input: {}", input);
        return Try.of(() -> {
                    List<Guest> guests = getGuests(input);
                    guestRepository.saveAll(guests);
                    Room room = roomRepository.findById(UUID.fromString(input.getRoomId())).orElseThrow(() -> new RoomNotFoundException());
                    Reservation reservation = reservationRepository.findByRoom(room).orElseThrow(()->  new ReservationNotFound());
                   // reservationRepository.findReservationByEndDateAfter(input.getStartDate(),input.getEndDate())
                    reservation.setGuests(guests);
                    reservationRepository.save(reservation);
                    RegisterVisitorOutput output = getRegisterVisitorOutput();
                    log.info("End register output: {}", output);
                    return output;
                }).toEither()
                .mapLeft(throwable -> errorMapper.mapError(throwable));
    }

    private static RegisterVisitorOutput getRegisterVisitorOutput() {
        RegisterVisitorOutput output = RegisterVisitorOutput.builder()
                .message("Post register")
                .build();
        return output;
    }

    private List<Guest> getGuests(RegisterVisitorInput input) {
        List<Guest> guests = new ArrayList<>();
        for (DataForVisitor dataForVisitor : input.getDataForVisitors()) {
            guests.add(conversionService.convert(dataForVisitor, Guest.class));
        }
        return guests;
    }
}
