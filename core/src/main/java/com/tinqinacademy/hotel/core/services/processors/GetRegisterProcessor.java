package com.tinqinacademy.hotel.core.services.processors;

import com.tinqinacademy.hotel.api.models.exceptions.ErrorMapper;
import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.operations.getRegister.GetRegisterInput;
import com.tinqinacademy.hotel.api.models.operations.getRegister.GetRegisterOperation;
import com.tinqinacademy.hotel.api.models.operations.getRegister.GetRegisterOutput;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.DataForVisitor;
import com.tinqinacademy.hotel.persistence.entity.Guest;
import com.tinqinacademy.hotel.persistence.repository.interfaces.GuestRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.tinqinacademy.hotel.core.services.specification.GuestSpecification.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetRegisterProcessor implements GetRegisterOperation {
    private final ConversionService conversionService;
    private final GuestRepository guestRepository;
    private final ErrorMapper errorMapper;

    @Override
    public Either<Errors, GetRegisterOutput> process(GetRegisterInput input) {
        log.info("Start getRegisteredUser input: {}", input.toString());

        return Try.of(() -> {
                    List<Guest> byDateRangeAndRoomNumber = guestRepository.findByDateRangeAndRoomNumber(input.getStartDate(), input.getEndDate(), input.getRoomNo());
                    Set<Guest> allGuestsInTimePeriod = new HashSet<>(byDateRangeAndRoomNumber);
                    List<Specification<Guest>> specifications = new ArrayList<>();
                    specifications.add(hasFirstName(input.getFirstName()));
                    specifications.add(hasLastName(input.getLastName()));
                    specifications.add(hasCardNumber(input.getIDCardNumber()));
                    specifications.add(hasCardIssueAuthority(input.getAuthority()));
                    specifications.add(hasCardValidity(input.getValidity()));
                    specifications.add(hasCardIssueDate(input.getDate()));
                    specifications.add(hasBirthDate(input.getBirthdate()));
                    List<Specification<Guest>> enteredFields = specifications.stream().filter(Objects::nonNull).toList();
                    Specification<Guest> finalSpecification = specificationBuilder(enteredFields);
                    List<Guest> all = guestRepository.findAll(finalSpecification);
                    Set<Guest> filteredGuests = new HashSet<>(all);
                    List<Guest> resultGuests = allGuestsInTimePeriod.stream()
                            .filter(filteredGuests::contains)
                            .toList();
                    GetRegisterOutput result = GetRegisterOutput
                            .builder()
                            .data(resultGuests.stream().
                                    map(request -> conversionService.convert(request, DataForVisitor.class)).toList())
                            .build();

                    log.info("End of getRegisteredUser result: {}", result);
                    return result;
                }).toEither()
                .mapLeft(throwable -> errorMapper.mapError(throwable));
    }

    public static <T> Specification<T> specificationBuilder(List<Specification<T>> specifications) {

        List<Specification<T>> specificationList = specifications.stream()
                .filter(Objects::nonNull)
                .toList();

        Specification<T> first = specificationList.getFirst();
        for (int i = 1; i < specificationList.size(); i++) {
            first = first.and(specificationList.get(i));
        }
        return first;
    }
}
