package com.tinqinacademy.hotel.core.services.converters;

import com.tinqinacademy.hotel.api.models.operations.registerVisitor.DataForVisitor;
import com.tinqinacademy.hotel.persistence.entity.Guest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FromGuestToDataForVisitor implements Converter<Guest, DataForVisitor> {
    @Override
    public DataForVisitor convert(Guest source) {
        return DataForVisitor.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .IDCardNumber(source.getIdCardNumber())
                .authority(source.getIdCardAuthority())
                .issueDate(source.getIdCardIssueDate())
                .birthdate(source.getBirthdate())
                .validity(source.getIdCardValidity())
                .build();
    }
}
