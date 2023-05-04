package com.svalero.toprestaurantsapi.controller;

import com.svalero.toprestaurantsapi.domain.Reserve;
import com.svalero.toprestaurantsapi.domain.Restaurant;
import com.svalero.toprestaurantsapi.domain.dto.ReserveInDTO;
import com.svalero.toprestaurantsapi.exception.*;
import com.svalero.toprestaurantsapi.service.ReserveService;
import com.svalero.toprestaurantsapi.service.RestaurantService;
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

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/reserves")
    public ResponseEntity<List<Reserve>> getReserves(@RequestParam(name = "isPaid", defaultValue = "") String isPaid) {
        if (isPaid.equals("")) {
            return ResponseEntity.ok(reserveService.findAll());
        } else {
            boolean paid = Boolean.parseBoolean(isPaid);
            return ResponseEntity.ok(reserveService.findAllByIsPaid(paid));
        }
    }

    @GetMapping("restaurants/{restaurantId}/reserves")
    public ResponseEntity<List<Reserve>> getReservesByRestaurantId(@PathVariable long restaurantId) throws RestaurantNotFoundException{
        Restaurant restaurant = restaurantService.findById(restaurantId);
        List<Reserve> reserves = reserveService.findByRestaurant(restaurant);
        return ResponseEntity.ok(reserves);
    }

    @GetMapping("/reserves/{id}")
    public ResponseEntity<Reserve> getReserves(@PathVariable long id) throws ReserveNotFoundException {
        Reserve reserve = reserveService.findById(id);
        return ResponseEntity.ok(reserve);
    }

    @PostMapping("/restaurants/{restaurantId}/reserves")
    public ResponseEntity<Reserve> addReserve(@PathVariable long restaurantId, @Valid @RequestBody ReserveInDTO reserveInDTO) throws RestaurantNotFoundException, CustomerNotFoundException, ShiftNotFoundException {
        Reserve newReserve = reserveService.addReserve(reserveInDTO, restaurantId);
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
