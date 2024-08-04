package com.tinqinacademy.hotel.core.services.converters;

import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.persistence.entity.Guest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FromRegisterVisitorToGuest implements Converter<RegisterVisitorInput, Guest> {
    @Override
    public Guest convert(RegisterVisitorInput source) {
        return Guest.builder()
                .build();
    }
}
