package com.tinqinacademy.hotel.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name ="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number",unique = true)
    private String phoneNumber;
    private LocalDate birthdate;
    @Column(unique = true)
    private String email;
    @OneToMany(targetEntity = Reservation.class,mappedBy = "user")
    private List<Reservation> reservations;
}
