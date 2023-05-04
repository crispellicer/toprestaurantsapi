package com.svalero.toprestaurantsapi.exception;

public class RestaurantNotFoundException extends Exception {

    public RestaurantNotFoundException(String message) {
        super(message);
    }

    public RestaurantNotFoundException() {
        super("Restaurant not found");
    }
}
