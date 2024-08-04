package com.tinqinacademy.hotel.core.services.processors;

import com.tinqinacademy.hotel.api.models.exceptions.ErrorMapper;
import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.operations.systemRoom.SystemRoomInput;
import com.tinqinacademy.hotel.api.models.operations.systemRoom.SystemRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.systemRoom.SystemRoomOutput;
import com.tinqinacademy.hotel.persistence.entity.Bed;
import com.tinqinacademy.hotel.persistence.entity.Room;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repository.interfaces.BedRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SystemRoomProcessor implements SystemRoomOperation {
    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;
    private final ErrorMapper errorMapper;

    @Override
    public Either<Errors, SystemRoomOutput> process(SystemRoomInput input) {
        log.info("START add input: {}", input);
        return Try.of(() -> {
                    List<Bed> bedSizes = getBedSizes(input);
                    Room room = buildRoom(input, bedSizes);
                    roomRepository.save(room);
                    Room byRoomNumberAndFloor = roomRepository.findByRoomNumberAndFloor(room.getRoomNumber(), room.getFloor());
                    SystemRoomOutput addRoomOutput = getSystemRoomOutput(byRoomNumberAndFloor);
                    log.info("END add result: {}", addRoomOutput);
                    return addRoomOutput;
                }).toEither()
                .mapLeft(throwable -> errorMapper.mapError(throwable));
    }

    private SystemRoomOutput getSystemRoomOutput(Room byRoomNumberAndFloor) {
        SystemRoomOutput addRoomOutput = SystemRoomOutput.builder()
                .id(byRoomNumberAndFloor.getId())
                .build();
        return addRoomOutput;
    }

    private Room buildRoom(SystemRoomInput input, List<Bed> bedSizes) {
        Room room = Room.builder()
                .roomNumber(input.getRoomNo())
                .floor(input.getFloor())
                .price(input.getPrice())
                .beds(bedSizes)
                .bathroomType(com.tinqinacademy.hotel.persistence.enums.BathroomType.getByCode(input.getBathroomType()))
                .build();
        return room;
    }

    private List<Bed> getBedSizes(SystemRoomInput input) {
        List<Bed> bedSizes = new ArrayList<>();
        for (String bedCode : input.getBeds()) {
            bedSizes.add(bedRepository.findBedByType(BedSize.getByCode(bedCode)));
        }
        return bedSizes;
    }
}
