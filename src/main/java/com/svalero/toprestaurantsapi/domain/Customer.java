package com.svalero.toprestaurantsapi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @NotBlank(message = "The field cannot be empty")
    @NotNull(message = "The field is required")
    private String name;
    @Column
    private String surname;
    @Column
    private String telephone;
    @Column
    private LocalDate birthDate;
    @Column
    private boolean vip;

    @OneToMany(mappedBy = "customer")
    @JsonBackReference(value = "customer_reserves")
    private List<Reserve> reserves;

}
