package com.svalero.toprestaurantsapi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;

@Entity(name = "shifts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    @NotNull(message = "The field is required")
    private LocalTime startTime;
    @Column
    @NotNull(message = "The field is required")
    private LocalTime endingTime;

    @OneToMany(mappedBy = "shift")
    @JsonBackReference(value = "shift_reserves")
    private List<Reserve> reserves;
}
