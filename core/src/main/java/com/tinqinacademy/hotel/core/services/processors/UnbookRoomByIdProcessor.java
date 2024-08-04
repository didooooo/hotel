package com.tinqinacademy.hotel.core.services.processors;

import com.tinqinacademy.hotel.api.models.exceptions.ErrorMapper;
import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomByIdInput;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomByIdOperation;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomByIdOutput;
import com.tinqinacademy.hotel.persistence.repository.interfaces.ReservationRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UnbookRoomByIdProcessor implements UnbookRoomByIdOperation {
    private final ReservationRepository reservationRepository;
    private final ErrorMapper errorMapper;

    @Override
    public Either<Errors, UnbookRoomByIdOutput> process(UnbookRoomByIdInput input) {
        log.info("Start deleteSpecificRoom input {}", input);
        return Try.of(() -> {
                    reservationRepository.deleteById(UUID.fromString(input.getBookingId()));
                    String message = "Successfully deleted! ";
                    UnbookRoomByIdOutput output = getUnbookRoomByIdOutput(message);
                    log.info("End deleteSpecificRoom output {}", output);
                    return output;
                }).toEither()
                .mapLeft(throwable -> errorMapper.mapError(throwable));
    }

    private static UnbookRoomByIdOutput getUnbookRoomByIdOutput(String message) {
        UnbookRoomByIdOutput output = UnbookRoomByIdOutput.builder()
                .message(message)
                .build();
        return output;
    }
}
