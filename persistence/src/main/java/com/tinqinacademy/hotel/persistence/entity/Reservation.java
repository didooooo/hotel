package com.tinqinacademy.hotel.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "user")
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "start_date",nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date",nullable = false)
    private LocalDate endDate;
    @Column(nullable = false)
    private BigDecimal price;
    @ManyToOne(targetEntity = Room.class)
    private Room room;
    @ManyToOne(targetEntity = User.class)
    private User user;
    @ManyToMany(targetEntity = Guest.class)
    private List<Guest> guests;

}
