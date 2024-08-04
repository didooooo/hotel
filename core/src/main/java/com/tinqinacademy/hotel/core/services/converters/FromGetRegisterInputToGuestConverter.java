package com.tinqinacademy.hotel.core.services.converters;

import com.tinqinacademy.hotel.api.models.operations.getRegister.GetRegisterInput;
import com.tinqinacademy.hotel.persistence.entity.Guest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FromGetRegisterInputToGuestConverter implements Converter<GetRegisterInput, Guest> {
    @Override
    public Guest convert(GetRegisterInput source) {
        return Guest.builder()
                .idCardValidity(source.getValidity())
                .birthdate(source.getBirthdate())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .idCardAuthority(source.getAuthority())
                .idCardNumber(source.getIDCardNumber())
                .idCardIssueDate(source.getDate())
                .build();
    }
}
