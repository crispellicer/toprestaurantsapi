package com.svalero.toprestaurantsapi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity(name = "addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String type;
    @Column
    @NotBlank(message = "The field cannot be empty")
    @NotNull(message = "The field is required")
    private String name;
    @Column
    @Min(value = 1)
    private int number;
    @Column
    private int postalCode;
    @Column
    @NotBlank(message = "The field cannot be empty")
    @NotNull(message = "The field is required")
    private String city;

    @OneToOne(mappedBy = "address")
    @JsonBackReference(value = "address_restaurant")
    private Restaurant restaurant;

}
