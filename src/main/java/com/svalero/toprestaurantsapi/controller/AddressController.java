package com.svalero.toprestaurantsapi.controller;

import com.svalero.toprestaurantsapi.domain.Address;
import com.svalero.toprestaurantsapi.exception.AddressNotFoundException;
import com.svalero.toprestaurantsapi.exception.ErrorMessage;
import com.svalero.toprestaurantsapi.service.AddressService;
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
public class AddressController {

    @Autowired
    private AddressService addressService;
    private final Logger logger = LoggerFactory.getLogger(ReserveController.class);

    @GetMapping("/addresses")
    public ResponseEntity<List<Address>> getAddresses(@RequestParam(name = "city", defaultValue = "") String city) {
        logger.debug("begin getAddresses");
        if (city.equals("")) {
            logger.debug("end getAddresses");
            return ResponseEntity.ok(addressService.findAll());
        } else {
            String cityy = city;
            logger.debug("end getAddresses");
            return ResponseEntity.ok(addressService.findByCity(cityy));
        }
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable long id) throws AddressNotFoundException {
        logger.debug("begin getAddress");
        Address address = addressService.findById(id);
        logger.debug("end getAddress");
        return ResponseEntity.ok(address);
    }

    @PostMapping("/addresses")
    public ResponseEntity<Address> addAddress(@Valid @RequestBody Address address) {
        logger.debug("begin addAddress");
        Address newAddress = addressService.addAddress(address);
        logger.debug("end addAddress");
        return ResponseEntity.status(HttpStatus.CREATED).body(newAddress);
    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable long id) throws AddressNotFoundException {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/addresses/{id}")
    public ResponseEntity<Address> modifyAddress(@PathVariable long id, @RequestBody Address address) throws AddressNotFoundException{
        logger.debug("begin modifyAddress");
        Address modifiedAddress = addressService.modifyAddress(id, address);
        logger.debug("end modifyAddress");
        return ResponseEntity.status(HttpStatus.OK).body(modifiedAddress);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleAddressNotFoundException(AddressNotFoundException anfe) {
        logger.error(anfe.getMessage(), anfe);
        ErrorMessage errorMessage = new ErrorMessage(404, anfe.getMessage());
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
