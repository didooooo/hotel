package com.tinqinacademy.hotel.core.services.processors;

import com.tinqinacademy.hotel.api.models.exceptions.ErrorMapper;
import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.operations.getRegister.GetRegisterInput;
import com.tinqinacademy.hotel.api.models.operations.getRegister.GetRegisterOperation;
import com.tinqinacademy.hotel.api.models.operations.getRegister.GetRegisterOutput;
import com.tinqinacademy.hotel.persistence.entity.Guest;
import com.tinqinacademy.hotel.persistence.repository.interfaces.GuestRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetRegisterProcessor implements GetRegisterOperation {
    private final ConversionService conversionService;
    private final GuestRepository guestRepository;
    private final ErrorMapper errorMapper;

    @Override
    public Either<Errors, GetRegisterOutput> process(GetRegisterInput input) {
        log.info("Start register input: {}", input);
        return Try.of(() -> {
                    GetRegisterOutput output = conversionService.convert(input, GetRegisterOutput.class);
                    Guest converted = conversionService.convert(input, Guest.class);
                    guestRepository.save(converted);
                    log.info("End register output: {}", output);
                    return output;

                }).toEither()
                .mapLeft(throwable -> errorMapper.mapError(throwable));
    }
}
