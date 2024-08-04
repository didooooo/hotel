package com.tinqinacademy.hotel.core.services.converters;

import com.tinqinacademy.hotel.api.models.operations.checkRoom.CheckRoomAvailabilityOutput;
import com.tinqinacademy.hotel.persistence.entity.Room;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomToCheckRoomAvailabilityOutput implements Converter<List<Room>, CheckRoomAvailabilityOutput> {
    @Override
    public CheckRoomAvailabilityOutput convert(List<Room> source) {
        List<String> ids = new ArrayList<>();
        for (Room room : source) {
            ids.add(String.valueOf(room.getId()));
        }
        return CheckRoomAvailabilityOutput.builder()
                .ids(ids)
                .build();
    }
}
