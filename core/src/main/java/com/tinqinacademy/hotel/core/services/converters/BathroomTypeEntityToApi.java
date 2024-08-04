package com.tinqinacademy.hotel.core.services.converters;

import com.tinqinacademy.hotel.persistence.enums.BathroomType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BathroomTypeEntityToApi implements Converter<BathroomType, com.tinqinacademy.hotel.api.models.enums.BathroomType> {
    @Override
    public com.tinqinacademy.hotel.api.models.enums.BathroomType convert(BathroomType source) {
        return com.tinqinacademy.hotel.api.models.enums.BathroomType.getByCode(source.toString());
    }
}
