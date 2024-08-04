package com.tinqinacademy.hotel.core.services.converters;

import com.tinqinacademy.hotel.api.models.operations.registerVisitor.DataForVisitor;
import com.tinqinacademy.hotel.persistence.entity.Guest;
import org.springframework.core.convert.converter.Converter;

public class FromDataForVisitorToGuest implements Converter<DataForVisitor, Guest> {
    @Override
    public Guest convert(DataForVisitor source) {
        return Guest.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .birthdate(source.getBirthdate())
                .idCardIssueDate(source.getIssueDate())
                .idCardAuthority(source.getAuthority())
                .idCardNumber(source.getIDCardNumber())
                .idCardValidity(source.getValidity())
                .build();
    }
}
