package com.tinqinacademy.hotel.core.services.converters;

import com.tinqinacademy.hotel.api.models.operations.getRoomById.GetRoomByIDOutput;
import com.tinqinacademy.hotel.persistence.entity.Bed;
import com.tinqinacademy.hotel.persistence.entity.Room;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomToGetRoomByIdOutput implements Converter<Room, GetRoomByIDOutput> {
    private final FromBedEntityToBedApi fromBedEntityToBedApi;
    private final BathroomTypeEntityToApi bathroomTypeEntityToApi;

    public RoomToGetRoomByIdOutput(FromBedEntityToBedApi fromBedEntityToBedApi, BathroomTypeEntityToApi bathroomTypeEntityToApi) {
        this.fromBedEntityToBedApi = fromBedEntityToBedApi;
        this.bathroomTypeEntityToApi = bathroomTypeEntityToApi;
    }

    @Override
    public GetRoomByIDOutput convert(Room source) {
        List<com.tinqinacademy.hotel.api.models.enums.Bed> beds = new ArrayList<>();
        for (Bed bed : source.getBeds()) {
            beds.add(fromBedEntityToBedApi.convert(bed));
        }
        return GetRoomByIDOutput.builder()
                .beds(beds)
                .floor(source.getFloor())
                .bathroomType(bathroomTypeEntityToApi.convert(source.getBathroomType()))
                .price(source.getPrice())
                .build();
    }
}
