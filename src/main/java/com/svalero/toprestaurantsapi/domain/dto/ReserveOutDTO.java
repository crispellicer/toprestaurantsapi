package com.svalero.toprestaurantsapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReserveOutDTO {

    private long id;
    private int people;
    private LocalDate reserveDate;
    private boolean isPaid;
 //   private String restaurantName;
 //   private String customerName;
}
