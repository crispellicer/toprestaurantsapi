package com.svalero.toprestaurantsapi.exception;

public class ShiftNotFoundException extends Exception {

    public ShiftNotFoundException(String message) {
        super(message);
    }

    public ShiftNotFoundException() {
        super("Address not found");
    }
}