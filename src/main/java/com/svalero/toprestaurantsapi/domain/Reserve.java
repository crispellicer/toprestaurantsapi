package com.svalero.toprestaurantsapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity(name = "reserves")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reserve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @Min(value = 1)
    private int people;
    @Column
    private int tables;
    @Column
    @NotNull(message = "Indicate reservation date")
    private LocalDate reserveDate;
    @Column
    private boolean isPaid;
    @Column
    private boolean allergic;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "shift_id")
    private Shift shift;

}
