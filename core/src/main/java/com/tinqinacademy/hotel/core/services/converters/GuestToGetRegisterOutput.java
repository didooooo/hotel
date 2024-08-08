package com.tinqinacademy.hotel.core.services.converters;

import com.tinqinacademy.hotel.api.models.operations.getRegister.GetRegisterInput;
import com.tinqinacademy.hotel.api.models.operations.getRegister.GetRegisterOutput;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.DataForVisitor;
import com.tinqinacademy.hotel.persistence.entity.Guest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class GuestToGetRegisterOutput implements Converter<List<Guest>, GetRegisterOutput> {
    public List<DataForVisitor> getData(List<Guest> data) {
        List<DataForVisitor> dataForVisitors = new ArrayList<>();
        for (Guest datum : data) {
            dataForVisitors.add(
                    DataForVisitor.builder()
                            .firstName(datum.getFirstName())
                            .lastName(datum.getLastName())
                            .birthdate(datum.getBirthdate())
                            .issueDate(datum.getIdCardIssueDate())
                            .IDCardNumber(datum.getIdCardNumber())
                            .authority(datum.getIdCardAuthority())
                            .validity(datum.getIdCardValidity())
                            .build()
            );
        }
        return dataForVisitors;
    }

    @Override
    public GetRegisterOutput convert(List<Guest> source) {
        return GetRegisterOutput.builder()
                .data(getData(source))
                .build();
    }
}
