package com.tinqinacademy.hotel.core.services.converters;

import com.tinqinacademy.hotel.persistence.entity.Bed;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FromStringToBebEntityConverter implements Converter<String, Bed> {
    @Override
    public Bed convert(String source) {
        return Bed.builder()
                .type(BedSize.getByCode(source))
                .count(BedSize.getByCode(source).getCount())
                .build();
    }
}
