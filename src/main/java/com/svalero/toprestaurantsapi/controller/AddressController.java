package com.svalero.toprestaurantsapi.controller;

import com.svalero.toprestaurantsapi.domain.Address;
import com.svalero.toprestaurantsapi.exception.AddressNotFoundException;
import com.svalero.toprestaurantsapi.exception.ErrorMessage;
import com.svalero.toprestaurantsapi.service.AddressService;
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

    @GetMapping("/addresses")
    public ResponseEntity<List<Address>> getAddresses(@RequestParam(name = "city", defaultValue = "") String city) {
        if (city.equals("")) {
            return ResponseEntity.ok(addressService.findAll());
        } else {
            String cityy = city;
            return ResponseEntity.ok(addressService.findByCity(cityy));
        }
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable long id) throws AddressNotFoundException {
        Address address = addressService.findById(id);
        return ResponseEntity.ok(address);
    }

    @PostMapping("/addresses")
    public ResponseEntity<Address> addAddress(@Valid @RequestBody Address address) {
        Address newAddress = addressService.addAddress(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAddress);
    }

    @DeleteMapping("/address/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable long id) throws AddressNotFoundException {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/address/{id}")
    public ResponseEntity<Address> modifyAddress(@PathVariable long id, @RequestBody Address address) throws AddressNotFoundException{
        Address modifiedAddress = addressService.modifyAddress(id, address);
        return ResponseEntity.status(HttpStatus.OK).body(modifiedAddress);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleAddressNotFoundException(AddressNotFoundException anfe) {
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
}
