package com.svalero.toprestaurantsapi.controller;

import com.svalero.toprestaurantsapi.domain.Customer;
import com.svalero.toprestaurantsapi.exception.CustomerNotFoundException;
import com.svalero.toprestaurantsapi.exception.ErrorMessage;
import com.svalero.toprestaurantsapi.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    private final Logger logger = LoggerFactory.getLogger(ReserveController.class);

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers(@RequestParam(name = "name", defaultValue = "") String name) {
        logger.debug("begin getCustomers");
        if (name.equals("")) {
            logger.debug("end getCustomers");
            return ResponseEntity.ok(customerService.findAll());
        } else {
            String customerName = name;
            logger.debug("end getCustomers");
            return ResponseEntity.ok(customerService.findByName(customerName));
        }
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable long id) throws CustomerNotFoundException {
        logger.debug("begin getCustomer");
        Customer customer = customerService.findById(id);
        logger.debug("end getCustomer");
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer) {
        logger.debug("begin addCustomer");
        Customer newCustomer = customerService.addCustomer(customer);
        logger.debug("end addCustomer");
        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable long id) throws CustomerNotFoundException {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> modifyCustomer(@PathVariable long id, @RequestBody Customer customer) throws CustomerNotFoundException{
        logger.debug("begin modifyCustomer");
        Customer modifiedCustomer = customerService.modifyCustomer(id, customer);
        logger.debug("end modifyCustomer");
        return ResponseEntity.status(HttpStatus.OK).body(modifiedCustomer);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleCustomerNotFoundException(CustomerNotFoundException cnfe) {
        logger.error(cnfe.getMessage(), cnfe);
        ErrorMessage errorMessage = new ErrorMessage(404, cnfe.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleBadRequestException(MethodArgumentNotValidException manve) {
        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        ErrorMessage badRequestErrorMessage = new ErrorMessage(400, "Bad Request", errors);
        return new ResponseEntity<>(badRequestErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception exception) {
        logger.error(exception.getMessage(), exception);
        ErrorMessage errorMessage = new ErrorMessage(500, "Internal Server Error");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}



