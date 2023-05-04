package com.svalero.toprestaurantsapi.exception;

public class AddressNotFoundException extends Exception {

    public AddressNotFoundException(String message) {
        super(message);
    }

    public AddressNotFoundException() {
        super("Address not found");
    }
}