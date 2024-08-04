package com.tinqinacademy.hotel.rest.controller;


import com.tinqinacademy.hotel.api.models.enums.BathroomType;
import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookTheSpecificRoomInput;
import com.tinqinacademy.hotel.api.models.operations.checkRoom.CheckRoomAvailabilityInput;
import com.tinqinacademy.hotel.api.models.operations.getRoomById.GetRoomByIDInput;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomByIdInput;
import com.tinqinacademy.hotel.core.services.processors.BookTheSpecificRoomProcessor;
import com.tinqinacademy.hotel.core.services.processors.CheckRoomAvailabilityProcessor;
import com.tinqinacademy.hotel.core.services.processors.GetRoomByIdProcessor;
import com.tinqinacademy.hotel.core.services.processors.UnbookRoomByIdProcessor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class HotelController extends BaseController {
    private final BookTheSpecificRoomProcessor bookTheSpecificRoomProcessor;
    private final CheckRoomAvailabilityProcessor checkRoomAvailabilityProcessor;
    private final UnbookRoomByIdProcessor unbookRoomByIdProcessor;
    private final GetRoomByIdProcessor getRoomByIdProcessor;

    @Autowired
    public HotelController(BookTheSpecificRoomProcessor bookTheSpecificRoomProcessor, CheckRoomAvailabilityProcessor checkRoomAvailabilityProcessor, UnbookRoomByIdProcessor unbookRoomByIdProcessor, GetRoomByIdProcessor getRoomByIdProcessor) {
        this.bookTheSpecificRoomProcessor = bookTheSpecificRoomProcessor;
        this.checkRoomAvailabilityProcessor = checkRoomAvailabilityProcessor;
        this.unbookRoomByIdProcessor = unbookRoomByIdProcessor;
        this.getRoomByIdProcessor = getRoomByIdProcessor;
    }

    @GetMapping(URLMapping.CHECK_ROOM_AVAILABILITY)
    public ResponseEntity<?> checkRoomAvailability(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam List<String> bedsInput, @RequestParam String bathroomType) {
        CheckRoomAvailabilityInput checkRoomAvailabilityInput =
                CheckRoomAvailabilityInput.builder()
                        .bathroomType(BathroomType.getByCode(bathroomType))
                        .endDate(endDate)
                        .beds(bedsInput)
                        .startDate(startDate)
                        .build();
        return super.handleTheEither(checkRoomAvailabilityProcessor.process(checkRoomAvailabilityInput));
    }

    @GetMapping(URLMapping.GET_ROOM_BY_ID)
    public ResponseEntity<?> getRoomByIDOutput(@PathVariable String roomId) {
        GetRoomByIDInput getRoomByIDInput =
                GetRoomByIDInput.builder()
                        .roomId(roomId)
                        .build();
        return super.handleTheEither(getRoomByIdProcessor.process(getRoomByIDInput));
    }

    @Operation(
            summary = "book a room",
            responses = {
                    @ApiResponse(responseCode = "200", description = "booked")
            }
    )
    @PostMapping(URLMapping.GET_ROOM_BY_ID)
    public ResponseEntity<?> bookSpecificRoom(@PathVariable("roomId") String roomId, @RequestBody BookTheSpecificRoomInput input) {
        input.setRoomId(roomId);
      return super.handleTheEither(bookTheSpecificRoomProcessor.process(input));
    }

    @DeleteMapping(URLMapping.UNBOOK_ROOM_BY_ID)
    public ResponseEntity<?> unbookRoomById(@PathVariable("bookingId") String bookingId) {
        UnbookRoomByIdInput input = UnbookRoomByIdInput.builder()
                .bookingId(bookingId)
                .build();
        return super.handleTheEither(unbookRoomByIdProcessor.process(input));
    }

}
