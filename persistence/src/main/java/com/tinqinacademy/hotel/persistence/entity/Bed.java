package com.tinqinacademy.hotel.persistence.entity;

import com.tinqinacademy.hotel.persistence.enums.BedSize;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "beds")
public class Bed {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private BedSize type;
    private Integer count;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bed bed = (Bed) o;
        return type == bed.type && Objects.equals(count, bed.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, count);
    }

}
