package com.tinqinacademy.hotel.core.services.converters;

import com.tinqinacademy.hotel.api.models.operations.getRegister.GetRegisterOutput;
import com.tinqinacademy.hotel.persistence.entity.Guest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class GuestToGetRegisterOutput implements Converter<Guest, GetRegisterOutput> {
    @Override
    public GetRegisterOutput convert(Guest source) {
        return GetRegisterOutput.builder()
                .date(source.getIdCardIssueDate())
                .IDCardNumber(source.getIdCardNumber())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .authority(source.getIdCardAuthority())
                .validity(source.getIdCardValidity())
                .build();
    }
}
