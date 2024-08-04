package com.tinqinacademy.hotel.core.services.processors;

import com.tinqinacademy.hotel.api.models.exceptions.ErrorMapper;
import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.exceptions.customExceptions.ReservationNotFound;
import com.tinqinacademy.hotel.api.models.exceptions.customExceptions.RoomNotFoundException;
import com.tinqinacademy.hotel.api.models.operations.systemDelete.SystemDeleteRoomInput;
import com.tinqinacademy.hotel.api.models.operations.systemDelete.SystemDeleteRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.systemDelete.SystemDeleteRoomOutput;
import com.tinqinacademy.hotel.persistence.entity.Reservation;
import com.tinqinacademy.hotel.persistence.entity.Room;
import com.tinqinacademy.hotel.persistence.repository.interfaces.ReservationRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SystemDeleteRoomProcessor implements SystemDeleteRoomOperation {
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final ErrorMapper errorMapper;

    @Override
    public Either<Errors, SystemDeleteRoomOutput> process(SystemDeleteRoomInput input) {
        log.info("Start delete room input: {}", input);
        return Try.of(() -> {
                    Optional<Room> roomById = roomRepository.findById(UUID.fromString(input.getId()));
                    if (roomById.isEmpty()) throw new RoomNotFoundException();
                    Optional<Reservation> byRoom = reservationRepository.findByRoom(roomById.get());
                    if(byRoom.isEmpty()) throw new ReservationNotFound();
                    roomRepository.deleteById(UUID.fromString(input.getId()));
                    SystemDeleteRoomOutput output = getSystemDeleteRoomOutput();
                    log.info("End delete room output: {}", output);
                    return output;
                }).toEither()
                .mapLeft(throwable -> errorMapper.mapError(throwable));
    }

    private static SystemDeleteRoomOutput getSystemDeleteRoomOutput() {
        SystemDeleteRoomOutput output = SystemDeleteRoomOutput.builder()
                .message("Successfully deleted")
                .build();
        return output;
    }


}
