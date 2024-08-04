package com.tinqinacademy.hotel.persistence.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.List;


public enum BedSize {
    SINGLE("single", 1),
    DOUBLE("double", 2),
    SMALL_DOUBLE("smallDouble", 2),
    KING_SIZE("kingSize", 4),
    QUEEN_SIZE("queenSize", 3),
    UNKNOWN("", 0);
    private final String code;
    private final Integer count;

    BedSize(String code, Integer count) {
        this.code = code;
        this.count = count;
    }

    @JsonCreator
    public static BedSize getByCode(String code) {
        for (BedSize value : BedSize.values()) {
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
    public Integer getCount(){
        return this.count;
    }

    public static List<String> getAllCodes() {
        List<String> allCodes = new ArrayList<>();
        for (BedSize value : BedSize.values()) {
            allCodes.add(value.code);
        }
        return allCodes;
    }
}
