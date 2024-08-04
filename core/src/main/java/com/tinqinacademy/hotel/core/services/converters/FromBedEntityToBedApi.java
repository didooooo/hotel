package com.tinqinacademy.hotel.core.services.converters;

import com.tinqinacademy.hotel.persistence.entity.Bed;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FromBedEntityToBedApi implements Converter<Bed, com.tinqinacademy.hotel.api.models.enums.Bed> {
    @Override
    public com.tinqinacademy.hotel.api.models.enums.Bed convert(Bed source) {
        return com.tinqinacademy.hotel.api.models.enums.Bed.getByCode(source.getType().getCode());
    }
}
