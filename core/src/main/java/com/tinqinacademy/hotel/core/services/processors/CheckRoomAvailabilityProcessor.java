package com.tinqinacademy.hotel.core.services.processors;

import com.tinqinacademy.hotel.api.models.exceptions.ErrorMapper;
import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.operations.checkRoom.CheckRoomAvailabilityInput;
import com.tinqinacademy.hotel.api.models.operations.checkRoom.CheckRoomAvailabilityOperation;
import com.tinqinacademy.hotel.api.models.operations.checkRoom.CheckRoomAvailabilityOutput;
import com.tinqinacademy.hotel.persistence.entity.Bed;
import com.tinqinacademy.hotel.persistence.entity.Reservation;
import com.tinqinacademy.hotel.persistence.entity.Room;
import com.tinqinacademy.hotel.persistence.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.repository.interfaces.ReservationRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static io.vavr.API.Case;

@Service
@Slf4j
@RequiredArgsConstructor
public class CheckRoomAvailabilityProcessor implements CheckRoomAvailabilityOperation {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final ErrorMapper errorMapper;

    @Override
    public Either<Errors, CheckRoomAvailabilityOutput> process(CheckRoomAvailabilityInput input) {
        log.info("Start getHotelRooms input {}", input);
        return Try.of(() -> {
                            List<Reservation> repositoryAll = reservationRepository.findAll();
                            List<Room> rooms = roomRepository.getAllRoomsThatDoesNotHaveReservation();
                            addRoomsToFreeRoomsFromReservation(input, repositoryAll, rooms);
                            List<String> freeIds = getFreeIds(input, rooms);
                            CheckRoomAvailabilityOutput converted = getCheckRoomAvailabilityOutput(freeIds);
                            log.info("End getHotelRooms output {}", converted);
                            return converted;
                        }
                ).toEither()
                .mapLeft(throwable -> {
                  return errorMapper.mapError(throwable);
                });
    }

    private static CheckRoomAvailabilityOutput getCheckRoomAvailabilityOutput(List<String> freeIds) {
        CheckRoomAvailabilityOutput converted = CheckRoomAvailabilityOutput.builder()
                .ids(freeIds)
                .build();
        return converted;
    }

    private List<String> getFreeIds(CheckRoomAvailabilityInput input, List<Room> rooms) {
        List<String> freeRooms = new ArrayList<>();
        for (Room room : rooms) {
            BathroomType bathroomType = BathroomType.getByCode(input.getBathroomType().getCode());
            checkBathroomType(input, room, bathroomType, freeRooms);
        }
        return freeRooms;
    }

    private void checkBathroomType(CheckRoomAvailabilityInput input, Room room, BathroomType bathroomType, List<String> freeRooms) {
        if (room.getBathroomType().equals(bathroomType)) {
            List<String> beds = input.getBeds();
            checkBeds(room, beds, freeRooms);
        }
    }

    private void checkBeds(Room room, List<String> beds, List<String> freeRooms) {
        for (Bed bed : room.getBeds()) {
            if (beds.contains(bed.getType().getCode())) {
                freeRooms.add(String.valueOf(room.getId()));
            }
        }
    }

    private void addRoomsToFreeRoomsFromReservation(CheckRoomAvailabilityInput input, List<Reservation> repositoryAll, List<Room> rooms) {
        for (Reservation reservation : repositoryAll) {
            if (reservation.getEndDate().isBefore(input.getStartDate())) {
                rooms.add(reservation.getRoom());
            }
        }
    }
}
