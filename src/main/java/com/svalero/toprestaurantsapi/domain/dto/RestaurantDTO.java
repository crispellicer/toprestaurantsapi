package com.svalero.toprestaurantsapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {

    @NotBlank(message = "The field cannot be empty")
    @NotNull(message = "The field is required")
    private String name;
    @NotBlank(message = "The field cannot be empty")
    @NotNull(message = "The field is required")
    private String timetable;
    private String type;
    @Min(value = 0)
    private double reservePrice;
    private boolean veganMenu;
    private String website;
    private long address;
}
