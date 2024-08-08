package com.tinqinacademy.hotel.restexport;

import com.tinqinacademy.hotel.api.models.mapping.URLMapping;
import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookTheSpecificRoomInput;
import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookTheSpecificRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.checkRoom.CheckRoomAvailabilityOutput;
import com.tinqinacademy.hotel.api.models.operations.getRegister.GetRegisterInput;
import com.tinqinacademy.hotel.api.models.operations.getRegister.GetRegisterOutput;
import com.tinqinacademy.hotel.api.models.operations.getRoomById.GetRoomByIDInput;
import com.tinqinacademy.hotel.api.models.operations.getRoomById.GetRoomByIDOutput;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.api.models.operations.systemDelete.SystemDeleteRoomInput;
import com.tinqinacademy.hotel.api.models.operations.systemDelete.SystemDeleteRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.systemPatch.SystemPatchRoomInput;
import com.tinqinacademy.hotel.api.models.operations.systemPatch.SystemPatchRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.systemPut.SystemPutRoomIdInput;
import com.tinqinacademy.hotel.api.models.operations.systemPut.SystemPutRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.systemRoom.SystemRoomInput;
import com.tinqinacademy.hotel.api.models.operations.systemRoom.SystemRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomByIdInput;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomByIdOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@FeignClient("hotel-service")
public interface RestExportHotel {
    //Hotel
    @GetMapping(URLMapping.CHECK_ROOM_AVAILABILITY)
    CheckRoomAvailabilityOutput checkRoomAvailability(@RequestParam LocalDate startDate,
                                                      @RequestParam LocalDate endDate,
                                                      @RequestParam List<String> bedsInput,
                                                      @RequestParam String bathroomType);

    @GetMapping(URLMapping.GET_ROOM_BY_ID)
    GetRoomByIDOutput getRoomByIDOutput(@PathVariable String roomId);

    @Operation(
            summary = "book a room",
            responses = {
                    @ApiResponse(responseCode = "200", description = "booked")
            }
    )
    @PostMapping(URLMapping.GET_ROOM_BY_ID)
    BookTheSpecificRoomOutput bookSpecificRoom(@PathVariable("roomId") String roomId, @RequestBody BookTheSpecificRoomInput input);


    @DeleteMapping(URLMapping.UNBOOK_ROOM_BY_ID)
    UnbookRoomByIdOutput unbookRoomById(@PathVariable("bookingId") String bookingId);

    //System
    @PostMapping(URLMapping.REGISTER)
    @Operation(
            summary = "Register visitors"
    )
    RegisterVisitorOutput register(@RequestBody RegisterVisitorInput input);

    @GetMapping(URLMapping.REGISTER)
    GetRegisterOutput register(@RequestParam LocalDate startDate,
                               @RequestParam LocalDate endDate,
                               @RequestParam String roomNo,
                               @RequestParam(required = false) String firstName,
                               @RequestParam(required = false) String lastName,
                               @RequestParam(required = false) String phoneNo,
                               @RequestParam(required = false) String IDCardNumber,
                               @RequestParam(required = false) String validity,
                               @RequestParam(required = false) String authority,
                               @RequestParam(required = false) String date
    );

    @DeleteMapping(URLMapping.SYSTEM_ROOM_BY_ID)
    @Operation(summary = "Remove a room ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "room removed")
            }
    )
    SystemDeleteRoomOutput deleteRoom(@PathVariable String roomId);

    @PatchMapping(value = URLMapping.SYSTEM_ROOM_BY_ID, consumes = "application/json-patch+json")
    SystemPatchRoomOutput patchRoom(@PathVariable String roomId, @RequestBody
    SystemPatchRoomInput input);

    @PutMapping(URLMapping.SYSTEM_ROOM_BY_ID)
    @Operation(
            summary = "edit a room",
            responses = {
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    SystemPutRoomOutput putRoom(@PathVariable String roomId, @RequestBody SystemPutRoomIdInput input);

    @PostMapping(URLMapping.POST_ROOM)
    @Operation(
            summary = "add a room",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Added a room")
            }
    )
    SystemRoomOutput postRoom(@RequestBody SystemRoomInput input);
}
