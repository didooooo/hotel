package com.tinqinacademy.hotel.persistence.entity;

import com.tinqinacademy.hotel.persistence.enums.BathroomType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private Integer floor;
    @Column(name = "bathroom_type")
    @Enumerated(EnumType.STRING)
    private BathroomType bathroomType;
    @ManyToMany(targetEntity = Bed.class)
    private List<Bed> beds;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(name = "room_number",nullable = false,unique = true)
    private String  roomNumber;


}
