package com.tinqinacademy.hotel.core.services.converters;

import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookTheSpecificRoomInput;
import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookTheSpecificRoomOutput;
import com.tinqinacademy.hotel.persistence.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FromBookTheSpecificRoomInputToUser implements Converter<BookTheSpecificRoomInput, User> {
    @Override
    public User convert(BookTheSpecificRoomInput source) {
        return User.builder()
                .phoneNumber(source.getPhoneNo())
                .email(source.getEmail())
                .birthdate(source.getBirthdate())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .build();
    }
}
