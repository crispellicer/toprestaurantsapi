package com.svalero.toprestaurantsapi.exception;

public class CustomerNotFoundException extends Exception {

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException() {
        super("Customer not found");
    }
}