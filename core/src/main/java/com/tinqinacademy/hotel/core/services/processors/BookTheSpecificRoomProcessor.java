package com.tinqinacademy.hotel.core.services.processors;

import com.tinqinacademy.hotel.api.models.exceptions.ErrorMapper;
import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.exceptions.customExceptions.BlankStringException;
import com.tinqinacademy.hotel.api.models.exceptions.customExceptions.ReservationAlreadyExists;
import com.tinqinacademy.hotel.api.models.exceptions.customExceptions.StartDateAfterEndDateException;
import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookTheSpecificRoomInput;
import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookTheSpecificRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookTheSpecificRoomOutput;
import com.tinqinacademy.hotel.persistence.entity.Reservation;
import com.tinqinacademy.hotel.persistence.entity.Room;
import com.tinqinacademy.hotel.persistence.entity.User;
import com.tinqinacademy.hotel.persistence.repository.interfaces.ReservationRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.RoomRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookTheSpecificRoomProcessor implements BookTheSpecificRoomOperation {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ConversionService conversionService;
    private final ReservationRepository reservationRepository;
    private final ErrorMapper errorMapper;

    @Override
    public Either<Errors, BookTheSpecificRoomOutput> process(BookTheSpecificRoomInput input) {
        log.info("Start getSpecificRoom input {}", input);
        return Try.of(() -> {
                    checkForBlankString(input);
                    checkDates(input);
                    User user = conversionService.convert(input, User.class);
                    saveUserIfDoesNotExists(user);
                    User foundUser = getByFirstNameAndLastNameAndPhoneNumber(user);
                    Room room = getRoomById(input);
                    double priceForReservation = getPriceForReservation(input, room);
                    Reservation reservation = buildReservation(input, room, foundUser, priceForReservation);
                    checkForReservation(room);
                    reservationRepository.save(reservation);
                    BookTheSpecificRoomOutput output = getBookTheSpecificRoomOutput(reservation);
                    log.info("End getSpecificRoom output {}", output);
                    return output;
                }).toEither()
                .mapLeft(throwable -> errorMapper.mapError(throwable));

    }

    private void checkForReservation(Room room) throws ReservationAlreadyExists {
        Optional<Reservation> byRoom = reservationRepository.findByRoom(room);
        if(byRoom.isPresent()){
            throw new ReservationAlreadyExists();
        }
    }

    private void checkDates(BookTheSpecificRoomInput input) throws StartDateAfterEndDateException {
        if(input.getStartDate().isAfter(input.getEndDate())){
            throw new StartDateAfterEndDateException();
        }
    }

    private void checkForBlankString(BookTheSpecificRoomInput input) throws Errors {
        if (input.getFirstName().isBlank()
                || input.getLastName().isBlank()
                || input.getPhoneNo().isBlank()) {
            throw new BlankStringException();
        }
    }

    private void saveUserIfDoesNotExists(User user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);
        }
    }

    private BookTheSpecificRoomOutput getBookTheSpecificRoomOutput(Reservation reservation) {
        BookTheSpecificRoomOutput output = BookTheSpecificRoomOutput.builder()
                .message(reservation.toString())
                .build();
        return output;
    }

    private Reservation buildReservation(BookTheSpecificRoomInput input, Room room, User found, double priceForReservation) {
        return Reservation.builder()
                .room(room)
                .user(found)
                .startDate(input.getStartDate())
                .endDate(input.getEndDate())
                .price(BigDecimal.valueOf(priceForReservation))
                .build();
    }

    private double getPriceForReservation(BookTheSpecificRoomInput input, Room room) {
        return Double.parseDouble(String.valueOf(room.getPrice()))
                * ChronoUnit.DAYS.between(input.getStartDate(), input.getEndDate());

    }

    private Room getRoomById(BookTheSpecificRoomInput input) {
        return roomRepository.findById(UUID.fromString(input.getRoomId())).get();
    }

    private User getByFirstNameAndLastNameAndPhoneNumber(User user) {
        return userRepository.findByFirstNameAndLastNameAndPhoneNumber(user.getFirstName(), user.getLastName(), user.getPhoneNumber());
    }
}
