package com.tinqinacademy.hotel.core.services.processors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.tinqinacademy.hotel.api.models.exceptions.ErrorMapper;
import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.exceptions.customExceptions.ReservationAlreadyExists;
import com.tinqinacademy.hotel.api.models.operations.systemPatch.SystemPatchRoomInput;
import com.tinqinacademy.hotel.api.models.operations.systemPatch.SystemPatchRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.systemPatch.SystemPatchRoomOutput;
import com.tinqinacademy.hotel.core.services.converters.FromStringToBebEntityConverter;
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
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SystemPatchRoomProcessor implements SystemPatchRoomOperation {
    private final ErrorMapper errorMapper;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final BedRepository bedRepository;
    private final ObjectMapper objectMapper;
    private final ConversionService conversionService;
    private final FromStringToBebEntityConverter fromStringToBebEntityConverter;

    @Override
    public Either<Errors, SystemPatchRoomOutput> process(SystemPatchRoomInput input) {
        log.info("Start patch room input: {}", input);
        return Try.of(() -> {
                    updateRoomByIdIfRoomExists(input);
                    SystemPatchRoomOutput output = SystemPatchRoomOutput.builder()
                            .id(String.valueOf(input.getId()))
                            .build();
                    log.info("End patch room output: {}", output);
                    return output;
                }).toEither()
                .mapLeft(throwable -> errorMapper.mapError(throwable));
    }

    private void updateRoomByIdIfRoomExists(SystemPatchRoomInput input) throws ReservationAlreadyExists {
        Room room = roomRepository.findById(UUID.fromString(input.getId())).get();
//        if(input.getBeds().isEmpty() || input.getBeds()==null){
//           input.getBeds()=null;
//        }
        Optional<Reservation> foundReservation = reservationRepository.findByRoom(room);
        if (foundReservation.isPresent()) throw new ReservationAlreadyExists();
        if (foundReservation.isEmpty()) {
            Room converted = conversionService.convert(input, Room.class);
            getBeds(input, converted);
            JsonNode roomNode = objectMapper.valueToTree(room);
            JsonNode inputNode = objectMapper.valueToTree(converted);
            try {
                JsonMergePatch patch = JsonMergePatch.fromJson(inputNode);
                JsonNode patchedNode = patch.apply(roomNode);
                Room patchedRoom = objectMapper.treeToValue(patchedNode, Room.class);
                log.info("Patched room {}", patchedRoom);
                roomRepository.save(patchedRoom);
            } catch (JsonPatchException | JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void getBeds(SystemPatchRoomInput input, Room converted) {
        if (input.getBeds() != null && !(input.getBeds().isEmpty())) {
            List<Bed> beds = new ArrayList<>();
            for (String bed : input.getBeds()) {
                beds.add(bedRepository.findBedByType(BedSize.getByCode(bed)));
            }
            converted.setBeds(beds);
        }
    }

    private static List<Bed> getBeds(SystemPatchRoomInput input) {
        List<Bed> beds = new ArrayList<>();
        for (String bed : input.getBeds()) {
            beds.add(Bed.builder().type(BedSize.getByCode(bed)).build());
        }
        return beds;
    }
}
