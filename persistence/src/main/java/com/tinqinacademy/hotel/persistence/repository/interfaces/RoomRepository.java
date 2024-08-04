package com.tinqinacademy.hotel.persistence.repository.interfaces;

import com.tinqinacademy.hotel.persistence.entity.Bed;
import com.tinqinacademy.hotel.persistence.entity.Room;
import com.tinqinacademy.hotel.persistence.enums.BathroomType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    @Modifying
    @Transactional
    @Query("UPDATE Room r SET r.roomNumber = :number, r.price = :price,r.bathroomType = :bathroomType WHERE r.id = :id")
    void updateById(UUID id, BathroomType bathroomType, String number, BigDecimal price);

    @Query(value = "select * from rooms where rooms.id not  in (select reservations.room_id from reservations);", nativeQuery = true)
    List<Room> getAllRoomsThatDoesNotHaveReservation();

    Room findByRoomNumberAndFloor(String number, Integer floor);
}
