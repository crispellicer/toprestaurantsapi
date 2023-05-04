package com.svalero.toprestaurantsapi.controller;

import com.svalero.toprestaurantsapi.domain.Reserve;
import com.svalero.toprestaurantsapi.domain.dto.ReserveDTO;
import com.svalero.toprestaurantsapi.exception.*;
import com.svalero.toprestaurantsapi.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ReserveController {

    @Autowired
    private ReserveService reserveService;

    @GetMapping("/reserves")
    public ResponseEntity<List<Reserve>> getReserves(@RequestParam(name = "paid", defaultValue = "") String paid) {
        if (paid.equals("")) {
            return ResponseEntity.ok(reserveService.findAll());
        } else {
            boolean isPaid = Boolean.parseBoolean(paid);
            return ResponseEntity.ok(reserveService.findByIsPaid(isPaid));
        }
    }

    @GetMapping("/reserves/{id}")
    public ResponseEntity<Reserve> getReserves(@PathVariable long id) throws ReserveNotFoundException {
        Reserve reserve = reserveService.findById(id);
        return ResponseEntity.ok(reserve);
    }

    @PostMapping("/reserves")
    public ResponseEntity<Reserve> addReserve(@Valid @RequestBody ReserveDTO reserveDTO) throws RestaurantNotFoundException, CustomerNotFoundException, ShiftNotFoundException {
        Reserve newReserve = reserveService.addReserve(reserveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newReserve);
    }

    @DeleteMapping ("/reserves/{id}")
    public ResponseEntity<Void> deleteReserve(@PathVariable long id) throws ReserveNotFoundException {
        reserveService.deleteReserve(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/reserves/{id}")
    public ResponseEntity<Reserve> modifyReserve(@PathVariable long id, @RequestBody Reserve reserve) throws ReserveNotFoundException {
        Reserve modifiedReserve = reserveService.modifyReserve(id, reserve);
        return ResponseEntity.status(HttpStatus.OK).body(modifiedReserve);
    }

    @ExceptionHandler(ReserveNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleReserveNotFoundException(ReserveNotFoundException rnfe) {
        ErrorMessage errorMessage = new ErrorMessage(404, rnfe.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleRestaurantNotFoundException(RestaurantNotFoundException rnfe) {
        ErrorMessage errorMessage = new ErrorMessage(404, rnfe.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleCustomerNotFoundException(CustomerNotFoundException cnfe) {
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

        ErrorMessage badRequestErrorMessage = new ErrorMessage(400, "Bad request", errors);
        return new ResponseEntity<>(badRequestErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        ErrorMessage errorMessage = new ErrorMessage(500, "Internal Server Error");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
