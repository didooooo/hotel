package com.tinqinacademy.hotel.api.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BathroomType {
    PRIVATE("private"),
    SHARED("shared"),
    UNKNOWN("");
    private final String code;

    BathroomType( String code) {
        this.code = code;
    }

    @JsonCreator
    public static BathroomType getByCode(String code) {
        // ObjectUtils.containsConstant(BedRoomType.values(),code,false); - return bed room type
        for (BathroomType value : BathroomType.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return UNKNOWN;
    }

    @JsonValue
    public String getCode() {
        return this.code;
    }

}
