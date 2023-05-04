package com.svalero.toprestaurantsapi.exception;

public class ReserveNotFoundException extends Exception {

    public ReserveNotFoundException(String message) {
        super(message);
    }

    public ReserveNotFoundException() {
        super("Reserve not found");
    }
}
