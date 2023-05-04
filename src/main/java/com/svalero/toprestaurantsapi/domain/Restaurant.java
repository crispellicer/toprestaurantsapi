package com.svalero.toprestaurantsapi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "restaurants")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @NotBlank(message = "The field cannot be empty")
    @NotNull(message = "The field is required")
    private String name;
    @Column
    @NotBlank(message = "The field cannot be empty")
    @NotNull(message = "The field is required")
    private String timetable;
    @Column
    private String type;
    @Column
    @Min(value = 0)
    private double reservePrice;
    @Column
    private boolean veganMenu;
    @Column
    private String website;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "restaurant")
    @JsonBackReference(value = "restaurant_reserves")
    private List<Reserve> reserves;
}
