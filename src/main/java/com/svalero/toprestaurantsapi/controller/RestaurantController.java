package com.svalero.toprestaurantsapi.controller;

import com.svalero.toprestaurantsapi.domain.Restaurant;
import com.svalero.toprestaurantsapi.exception.ErrorMessage;
import com.svalero.toprestaurantsapi.exception.RestaurantNotFoundException;
import com.svalero.toprestaurantsapi.service.RestaurantService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getRestaurants(@RequestParam(name = "veganmenu", defaultValue = "") String veganmenu) {
        if (veganmenu.equals("")) {
            return ResponseEntity.ok(restaurantService.findAll());
        } else {
            boolean veganMenu = Boolean.parseBoolean(veganmenu);
            return ResponseEntity.ok(restaurantService.findByVeganMenu(veganMenu));
        }
    }

    @GetMapping("/restaurants/{id}")
    public ResponseEntity<Restaurant> getRestaurants(@PathVariable long id) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantService.findById(id);
        return ResponseEntity.ok(restaurant);
    }

    @PostMapping("/restaurants")
    public ResponseEntity<Restaurant> addRestaurant(@Valid @RequestBody Restaurant restaurant) {
        Restaurant newRestaurant = restaurantService.addRestaurant(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRestaurant);
    }

    @DeleteMapping ("/restaurants/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable long id) throws RestaurantNotFoundException {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/restaurants/{id}")
    public ResponseEntity<Restaurant> modifyRestaurant(@PathVariable long id, @RequestBody Restaurant restaurant) throws RestaurantNotFoundException {
        Restaurant modifiedRestaurant = restaurantService.modifyRestaurant(id, restaurant);
        return ResponseEntity.status(HttpStatus.OK).body(modifiedRestaurant);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleRestaurantNotFoundException(RestaurantNotFoundException rnfe) {
        ErrorMessage errorMessage = new ErrorMessage(404, rnfe.getMessage());
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
