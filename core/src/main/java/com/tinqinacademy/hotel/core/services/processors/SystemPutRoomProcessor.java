package com.tinqinacademy.hotel.core.services.processors;

import com.tinqinacademy.hotel.api.models.exceptions.ErrorMapper;
import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.exceptions.customExceptions.ReservationIsPresent;
import com.tinqinacademy.hotel.api.models.exceptions.customExceptions.RoomNotFoundException;
import com.tinqinacademy.hotel.api.models.operations.systemPut.SystemPutRoomIdInput;
import com.tinqinacademy.hotel.api.models.operations.systemPut.SystemPutRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.systemPut.SystemPutRoomOutput;
import com.tinqinacademy.hotel.persistence.entity.Bed;
import com.tinqinacademy.hotel.persistence.entity.Reservation;
import com.tinqinacademy.hotel.persistence.entity.Room;
import com.tinqinacademy.hotel.persistence.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repository.interfaces.BedRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.ReservationRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class SystemPutRoomProcessor implements SystemPutRoomOperation {
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final BedRepository bedRepository;
    private final ErrorMapper errorMapper;

    @Override
    public Either<Errors, SystemPutRoomOutput> process(SystemPutRoomIdInput input) {
        log.info("Start put room input: {}", input);
        return Try.of(() -> {
                    Room byId = getRoom(input);
                    searchForReservation(byId);
                    List<Bed> beds = getBeds(input);
                    Set<Bed> bedSet = new HashSet<>(byId.getBeds());
                    bedSet.addAll(beds);
                    Room built = buildRoom(input, bedSet, byId);
                    roomRepository.save(built);
                    SystemPutRoomOutput output = getSystemPutRoomOutput(input);
                    log.info("End put room output: {}", output);
                    return output;
                }).toEither()
                .mapLeft(throwable -> errorMapper.mapError(throwable));
    }

    private void searchForReservation(Room byId) throws ReservationIsPresent {
        Optional<Reservation> foundReservation = reservationRepository.findByRoom(byId);
        if (foundReservation.isPresent()) {
            throw new ReservationIsPresent();
        }
    }

    private Room getRoom(SystemPutRoomIdInput input) throws RoomNotFoundException {
        Room byId = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException());
        return byId;
    }

    private SystemPutRoomOutput getSystemPutRoomOutput(SystemPutRoomIdInput input) {
        SystemPutRoomOutput output = SystemPutRoomOutput.builder()
                .id(String.valueOf(input.getRoomId()))
                .build();
        return output;
    }

    private static Room buildRoom(SystemPutRoomIdInput input, Set<Bed> bedSet, Room byId) {
        Room built = Room.builder()
                .bathroomType(BathroomType.getByCode(input.getBathroomType()))
                .roomNumber(input.getRoomNo())
                .price(input.getPrice())
                .beds(new ArrayList<>(bedSet))
                .floor(byId.getFloor())
                .id(byId.getId())
                .build();
        return built;
    }

    private List<Bed> getBeds(SystemPutRoomIdInput input) {
        List<Bed> beds = new ArrayList<>();
        for (String bedCode : input.getBeds()) {
            beds.add(bedRepository.findBedByType(BedSize.getByCode(bedCode)));
        }
        return beds;
    }
}
