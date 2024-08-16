package com.tinqinacademy.hotel.restexport;

import com.tinqinacademy.hotel.api.models.mapping.URLMapping;
import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookTheSpecificRoomInput;
import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookTheSpecificRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.checkRoom.CheckRoomAvailabilityOutput;
import com.tinqinacademy.hotel.api.models.operations.getRegister.GetRegisterOutput;
import com.tinqinacademy.hotel.api.models.operations.getRoomById.GetRoomByIDOutput;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.api.models.operations.systemDelete.SystemDeleteRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.systemPatch.SystemPatchRoomInput;
import com.tinqinacademy.hotel.api.models.operations.systemPatch.SystemPatchRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.systemPut.SystemPutRoomIdInput;
import com.tinqinacademy.hotel.api.models.operations.systemPut.SystemPutRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.systemRoom.SystemRoomInput;
import com.tinqinacademy.hotel.api.models.operations.systemRoom.SystemRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomByIdOutput;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Headers({"Content-Type: application/json"})
public interface RestExportHotel {
    //Hotel
    @RequestLine("GET /api/hotel/rooms?startDate={startDate}&endDate={endDate}&bedsInput={bedsInput}&bathroomType={bathroomType}")
    //  @GetMapping(URLMapping.CHECK_ROOM_AVAILABILITY)
    CheckRoomAvailabilityOutput checkRoomAvailability(@Param("startDate") LocalDate startDate,
                                                      @Param("endDate") LocalDate endDate,
                                                      @Param("bedsInput") List<String> bedsInput,
                                                      @Param("bathroomType") String bathroomType);

    @RequestLine("GET /api/hotel/{roomId}")
        // @GetMapping(URLMapping.GET_ROOM_BY_ID)
    GetRoomByIDOutput getRoomByIDOutput(@Param("roomId") String roomId);

    //    @Operation(
//            summary = "book a room",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "booked")
//            }
//    )
    // @PostMapping(URLMapping.GET_ROOM_BY_ID)
    @RequestLine("POST /api/hotel/{roomId}?input={input}")
    BookTheSpecificRoomOutput bookSpecificRoom(@Param("roomId") String roomId, @Param("input") BookTheSpecificRoomInput input);


    // @DeleteMapping(URLMapping.UNBOOK_ROOM_BY_ID)
    @RequestLine("DELETE /api/hotel/{bookingId}")
    UnbookRoomByIdOutput unbookRoomById(@Param("bookingId") String bookingId);

    //System

    //    @PostMapping(URLMapping.REGISTER)
//    @Operation(
//            summary = "Register visitors"
//    )
    @RequestLine("POST /system/register?input={input}")
    RegisterVisitorOutput register(@Param("input") RegisterVisitorInput input);

    // @GetMapping(URLMapping.REGISTER)
    @RequestLine("GET /system/register?startDate={startDate}&endDate={endDate}&roomNo={roomNo}" +
            "&firstName={firstName}&lastName={lastName}&phoneNo={phoneNo}" +
            "&IDCardNumber={IDCardNumber}&validity={validity}&" +
            "authority={authority}&date={date}")
    GetRegisterOutput register(@Param("startDate") LocalDate startDate,
                               @Param("endDate") LocalDate endDate,
                               @Param("roomNo") String roomNo,
                               @Param("firstName") String firstName,
                               @Param("lastName") String lastName,
                               @Param("phoneNo") String phoneNo,
                               @Param("IDCardNumber") String IDCardNumber,
                               @Param("validity") String validity,
                               @Param("authority") String authority,
                               @Param("date") String date
    );

    @RequestLine("DELETE /system/room/{roomId}")
//   // @DeleteMapping(URLMapping.SYSTEM_ROOM_BY_ID)
//    @Operation(summary = "Remove a room ",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "room removed")
//            }
//    )
    SystemDeleteRoomOutput deleteRoom(@Param("roomId") String roomId);

    @RequestLine("PATCH /system/room/{roomId}?input={input}")
        //  @PatchMapping(value = URLMapping.SYSTEM_ROOM_BY_ID, consumes = "application/json-patch+json")
    SystemPatchRoomOutput patchRoom(@Param("roomId") String roomId, @Param("input")
    SystemPatchRoomInput input);

    @RequestLine("PUT /system/room/{roomId}?input={input}")
        //@PutMapping(URLMapping.SYSTEM_ROOM_BY_ID)
//    @Operation(
//            summary = "edit a room",
//            responses = {
//                    @ApiResponse(responseCode = "403", description = "Forbidden")
//            }
//    )
    SystemPutRoomOutput putRoom(@Param("roomId") String roomId, @Param("input") SystemPutRoomIdInput input);

    @RequestLine("POST /system/room?input={input}")
//   // @PostMapping(URLMapping.POST_ROOM)
//    @Operation(
//            summary = "add a room",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "Added a room")
//            }
//    )
    SystemRoomOutput postRoom(@Param("input") SystemRoomInput input);
}
