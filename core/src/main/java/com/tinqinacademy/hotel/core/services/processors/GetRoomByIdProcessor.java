package com.tinqinacademy.hotel.core.services.processors;

import com.tinqinacademy.hotel.api.models.enums.Bed;
import com.tinqinacademy.hotel.api.models.exceptions.ErrorMapper;
import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.exceptions.customExceptions.RoomNotFoundException;
import com.tinqinacademy.hotel.api.models.operations.getRoomById.GetRoomByIDInput;
import com.tinqinacademy.hotel.api.models.operations.getRoomById.GetRoomByIDOutput;
import com.tinqinacademy.hotel.api.models.operations.getRoomById.GetRoomByIdOperation;
import com.tinqinacademy.hotel.persistence.entity.Room;
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
public class GetRoomByIdProcessor implements GetRoomByIdOperation {
    private final RoomRepository roomRepository;
    private final ConversionService conversionService;
    private final ErrorMapper errorMapper;

    @Override
    public Either<Errors, GetRoomByIDOutput> process(GetRoomByIDInput input) {
        log.info("Start getHotelRoomById input {}", input);
        return Try.of(() -> {
                    Room foundRoom = getRoom(input);
                    List<Bed> beds = getBeds(foundRoom);
                    GetRoomByIDOutput converted = conversionService.convert(foundRoom, GetRoomByIDOutput.class);
                    converted.setBeds(beds);
                    log.info("End getHotelRoomById output {}", converted);
                    return converted;
                }).toEither()
                .mapLeft(throwable -> errorMapper.mapError(throwable));
    }

    private Room getRoom(GetRoomByIDInput input) throws RoomNotFoundException {
        Optional<Room> found = roomRepository.findById(UUID.fromString(input.getRoomId()));
        if (found.isEmpty()) throw new RoomNotFoundException();
        return found.get();
    }

    private List<Bed> getBeds(Room found) {
        List<Bed> beds = new ArrayList<>();
        for (com.tinqinacademy.hotel.persistence.entity.Bed bedSize : found.getBeds()) {
            beds.add(conversionService.convert(bedSize, Bed.class));
        }
        return beds;
    }
}
