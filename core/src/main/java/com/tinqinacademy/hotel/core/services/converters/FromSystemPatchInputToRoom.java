package com.tinqinacademy.hotel.core.services.converters;

import com.tinqinacademy.hotel.api.models.operations.systemPatch.SystemPatchRoomInput;
import com.tinqinacademy.hotel.persistence.entity.Bed;
import com.tinqinacademy.hotel.persistence.entity.Room;
import com.tinqinacademy.hotel.persistence.enums.BathroomType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FromSystemPatchInputToRoom implements Converter<SystemPatchRoomInput, Room> {

    @Override
    public Room convert(SystemPatchRoomInput source) {
        BathroomType bathroomType = BathroomType.getByCode(source.getBathroomType());
        if (BathroomType.getByCode(source.getBathroomType()).equals(BathroomType.UNKNOWN)) {
            bathroomType = null;
        }

        return Room.builder()
                .price(source.getPrice())
                .roomNumber(source.getRoomNumber())
                .bathroomType(bathroomType)
                .build();
    }
}
