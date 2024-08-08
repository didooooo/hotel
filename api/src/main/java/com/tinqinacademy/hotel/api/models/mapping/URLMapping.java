package com.tinqinacademy.hotel.api.models.mapping;

public class URLMapping {
    public static final String CHECK_ROOM_AVAILABILITY = "/api/hotel/rooms";
    public static final String GET_ROOM_BY_ID = "/api/hotel/{roomId}";
    public static final String UNBOOK_ROOM_BY_ID = "/api/hotel/{bookingId}";
    public static final String SYSTEM_ROOM_BY_ID = "/system/room/{roomId}";
    public static final String REGISTER = "/system/register";
    public static final String POST_ROOM = "/system/room";
}
