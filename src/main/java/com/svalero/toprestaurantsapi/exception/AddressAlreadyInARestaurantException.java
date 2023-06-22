package com.svalero.toprestaurantsapi.exception;

public class AddressAlreadyInARestaurantException extends Exception {

    public AddressAlreadyInARestaurantException(String message) {
        super(message);
    }

    public AddressAlreadyInARestaurantException() {
        super("The address already belongs to a restaurant");
    }
}