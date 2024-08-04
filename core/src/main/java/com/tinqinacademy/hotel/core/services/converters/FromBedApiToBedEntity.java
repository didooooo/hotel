package com.tinqinacademy.hotel.core.services.converters;

import com.tinqinacademy.hotel.api.models.enums.Bed;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FromBedApiToBedEntity implements Converter<Bed, com.tinqinacademy.hotel.persistence.entity.Bed> {
    @Override
    public com.tinqinacademy.hotel.persistence.entity.Bed convert(Bed source) {
        return com.tinqinacademy.hotel.persistence.entity.Bed.builder()
                .count(source.getCount())
                .type(BedSize.getByCode(source.toString()))
                .build();
    }
}
