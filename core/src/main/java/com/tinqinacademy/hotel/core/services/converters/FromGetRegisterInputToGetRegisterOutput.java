package com.tinqinacademy.hotel.core.services.converters;

import com.tinqinacademy.hotel.api.models.operations.getRegister.GetRegisterInput;
import com.tinqinacademy.hotel.api.models.operations.getRegister.GetRegisterOutput;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.DataForVisitor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class FromGetRegisterInputToGetRegisterOutput implements Converter<GetRegisterInput, GetRegisterOutput> {
    public DataForVisitor getData (GetRegisterInput source){
        return DataForVisitor.builder()
                .validity(LocalDate.parse(source.getValidity()))
                .phoneNo(source.getPhoneNo())
                .birthdate(LocalDate.parse(source.getBirthdate()))
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .IDCardNumber(source.getIDCardNumber())
                .authority(source.getAuthority())
                .issueDate(LocalDate.parse(source.getDate()))
                .build();
    }
    @Override
    public GetRegisterOutput convert(GetRegisterInput source) {
        return GetRegisterOutput.builder()
                .endDate(source.getEndDate())
                .startDate(source.getStartDate())
                .data(List.of(getData(source)))
                .build();
    }
}
