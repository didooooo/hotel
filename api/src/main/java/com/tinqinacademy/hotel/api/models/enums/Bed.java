package com.tinqinacademy.hotel.api.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Bed {
    SINGLE("single", 1),
    DOUBLE("double", 2),
    SMALL_DOUBLE("smallDouble", 2),
    KING_SIZE("kingSize", 4),
    QUEEN_SIZE("queenSize", 3),
    UNKNOWN("", 2);
    private final String code;
    private final Integer count;

    Bed(String code, Integer count) {
        this.code = code;
        this.count = count;
    }
    @JsonCreator
    public static Bed getByCode(String code){
        for (Bed value : Bed.values()) {
            if(value.code.equals(code)){
                return value;
            }
        }
        return UNKNOWN;
    }
    @JsonValue
    public String toString(){
        return this.code;
    }
    public Integer getCount(){
        return count;
    }
}
