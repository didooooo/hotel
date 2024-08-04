package com.tinqinacademy.hotel.core.services.converters;

import com.tinqinacademy.hotel.api.models.operations.getRegister.GetRegisterInput;
import com.tinqinacademy.hotel.api.models.operations.getRegister.GetRegisterOutput;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FromGetRegisterInputToGetRegisterOutput implements Converter<GetRegisterInput, GetRegisterOutput> {
    @Override
    public GetRegisterOutput convert(GetRegisterInput source) {
        return GetRegisterOutput.builder()
                .date(source.getDate())
                .endDate(source.getEndDate())
                .authority(source.getAuthority())
                .IDCardNumber(source.getIDCardNumber())
                .phoneNo(source.getPhoneNo())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .startDate(source.getStartDate())
                .validity(source.getValidity())
                .build();
    }
}
