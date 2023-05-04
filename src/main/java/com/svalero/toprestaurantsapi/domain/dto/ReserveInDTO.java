package com.svalero.toprestaurantsapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReserveInDTO {

    @Min(value = 1)
    private int people;
    private int tables;
    @NotNull(message = "Indicate reservation date")
    private LocalDate reserveDate;
    private boolean isPaid;
    private boolean allergic;
    @Min(value = 1)
    private long customer;
    @Min(value = 1)
    private long shift;
}
