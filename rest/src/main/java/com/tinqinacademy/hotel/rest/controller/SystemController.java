package com.tinqinacademy.hotel.rest.controller;

import com.tinqinacademy.hotel.api.models.operations.getRegister.GetRegisterInput;
import com.tinqinacademy.hotel.api.models.operations.getRegister.GetRegisterOperation;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorOperation;
import com.tinqinacademy.hotel.api.models.operations.systemDelete.SystemDeleteRoomInput;
import com.tinqinacademy.hotel.api.models.operations.systemDelete.SystemDeleteRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.systemPatch.SystemPatchRoomInput;
import com.tinqinacademy.hotel.api.models.operations.systemPatch.SystemPatchRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.systemPut.SystemPutRoomIdInput;
import com.tinqinacademy.hotel.api.models.operations.systemPut.SystemPutRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.systemRoom.SystemRoomInput;
import com.tinqinacademy.hotel.api.models.operations.systemRoom.SystemRoomOperation;
import com.tinqinacademy.hotel.core.services.processors.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
public class SystemController extends BaseController {
    private final GetRegisterOperation getRegisterProcessor;
    private final RegisterVisitorOperation registerVisitorProcessor;
    private final SystemRoomOperation systemRoomProcessor;
    private final SystemPutRoomOperation systemPutRoomProcessor;
    private final SystemPatchRoomOperation systemPatchRoomProcessor;
    private final SystemDeleteRoomOperation systemDeleteRoomProcessor;

    public SystemController(GetRegisterOperation getRegisterProcessor, RegisterVisitorOperation registerVisitorProcessor, SystemRoomOperation systemRoomProcessor, SystemPutRoomOperation systemPutRoomProcessor, SystemPatchRoomOperation systemPatchRoomProcessor, SystemDeleteRoomOperation systemDeleteRoomProcessor) {
        this.getRegisterProcessor = getRegisterProcessor;
        this.registerVisitorProcessor = registerVisitorProcessor;
        this.systemRoomProcessor = systemRoomProcessor;
        this.systemPutRoomProcessor = systemPutRoomProcessor;
        this.systemPatchRoomProcessor = systemPatchRoomProcessor;
        this.systemDeleteRoomProcessor = systemDeleteRoomProcessor;
    }

    @PostMapping(URLMapping.REGISTER)
    @Operation(
            summary = "Register visitors"
    )
    public ResponseEntity<?> register(@RequestBody RegisterVisitorInput input) {
        return super.handleTheEither(registerVisitorProcessor.process(input));
    }

    @GetMapping(URLMapping.REGISTER)
    public ResponseEntity<?> register(@RequestParam LocalDate startDate,
                                                      @RequestParam LocalDate endDate,
                                                      @RequestParam String firstName,
                                                      @RequestParam String lastName,
                                                      @RequestParam String phoneNo,
                                                      @RequestParam String IDCardNumber,
                                                      @RequestParam String validity,
                                                      @RequestParam String authority,
                                                      @RequestParam LocalDate date,
                                                      @RequestParam Integer roomNo)  {

        GetRegisterInput input = GetRegisterInput.builder()
                .date(date)
                .endDate(endDate)
                .startDate(startDate)
                .firstName(firstName)
                .IDCardNumber(IDCardNumber)
                .roomNo(roomNo)
                .phoneNo(phoneNo)
                .validity(LocalDate.parse(validity))
                .lastName(lastName)
                .authority(authority)
                .build();
        return super.handleTheEither(getRegisterProcessor.process(input));
    }

    @DeleteMapping(URLMapping.SYSTEM_ROOM_BY_ID)
    @Operation(summary = "Remove a room ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "room removed")
            }
    )
    public ResponseEntity<?> deleteRoom(@PathVariable String roomId) {
        SystemDeleteRoomInput input =
                SystemDeleteRoomInput
                        .builder()
                        .id(roomId)
                        .build();
        return super.handleTheEither(systemDeleteRoomProcessor.process(input));
    }

    @PatchMapping(value = URLMapping.SYSTEM_ROOM_BY_ID,consumes = "application/json-patch+json")
    public ResponseEntity<?> patchRoom(@PathVariable String roomId, @RequestBody
    SystemPatchRoomInput input) {
        SystemPatchRoomInput result = SystemPatchRoomInput.builder()
                .id(roomId)
                .bathroomType(input.getBathroomType())
                .roomNumber(input.getRoomNumber())
                .price(input.getPrice())
                .beds(input.getBeds())
                .build();
        return handleTheEither(systemPatchRoomProcessor.process(result));
    }

    @PutMapping(URLMapping.SYSTEM_ROOM_BY_ID)
    @Operation(
            summary = "edit a room",
            responses = {
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    public ResponseEntity<?> putRoom(@PathVariable String roomId, @RequestBody SystemPutRoomIdInput input) {
        input.setRoomId(UUID.fromString(roomId));
        return handleTheEither(systemPutRoomProcessor.process(input));
    }

    @PostMapping(URLMapping.POST_ROOM)
    @Operation(
            summary = "add a room",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Added a room")
            }
    )
    public ResponseEntity<?> postRoom(@RequestBody SystemRoomInput input) {
        return handleTheEither(systemRoomProcessor.process(input));
    }

}
