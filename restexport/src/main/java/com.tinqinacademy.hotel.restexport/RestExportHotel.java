package com.tinqinacademy.hotel.restexport;

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

import java.time.LocalDate;
import java.util.List;

//@FeignClient(name = "hotel", url = "${spring.boot.admin.client.instance.service-url}", configuration = FeignConfig.class)
@Headers({"Content-Type: application/json"})
public interface RestExportHotel {
    //Hotel

    @RequestLine("POST /system/register?input={input}")
    RegisterVisitorOutput register(@Param("input") RegisterVisitorInput input);

    @RequestLine("GET /api/hotel/rooms?startDate={startDate}&endDate={endDate}&bedsInput={bedsInput}&bathroomType={bathroomType}")
    CheckRoomAvailabilityOutput checkRoomAvailability(//@RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam List<String> bedsInput, @RequestParam String bathroomType);
                                                      @Param("startDate") LocalDate startDate,
                                                      @Param("endDate") LocalDate endDate,
                                                      @Param("bedsInput") List<String> bedsInput,
                                                      @Param("bathroomType") String bathroomType);

    @RequestLine("GET /api/hotel/{roomId}")
    GetRoomByIDOutput getRoomByIDOutput(@Param("roomId") String roomId);

    @RequestLine("POST /api/hotel/{roomId}?input={input}")
    BookTheSpecificRoomOutput bookSpecificRoom(@Param("roomId") String roomId,
                                               @Param("input") BookTheSpecificRoomInput input);

    @RequestLine("DELETE /api/hotel/{bookingId}")
    UnbookRoomByIdOutput unbookRoomById(@Param("bookingId") String bookingId);

    //System

    @RequestLine("GET /system/register?startDate={startDate}&endDate={endDate}&roomNo={roomNo}" +
            "&firstName={firstName}&lastName={lastName}&phoneNo={phoneNo}" +
            "&IDCardNumber={IDCardNumber}&validity={validity}&" +
            "authority={authority}&date={date}")
    GetRegisterOutput register(
            @Param("startDate") LocalDate startDate,
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
    SystemDeleteRoomOutput deleteRoom(@Param("roomId") String roomId);

    @RequestLine("PATCH /system/room/{roomId}?input={input}")
    SystemPatchRoomOutput patchRoom(@Param("roomId") String roomId, @Param("input")
    SystemPatchRoomInput input);

    @RequestLine("PUT /system/room/{roomId}?input={input}")
    SystemPutRoomOutput putRoom(@Param("roomId") String roomId, @Param("input") SystemPutRoomIdInput input);

    @RequestLine("POST /system/room?input={input}")
    SystemRoomOutput postRoom(@Param("input") SystemRoomInput input);
}
