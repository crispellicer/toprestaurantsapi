package com.svalero.toprestaurantsapi.controller;

import com.svalero.toprestaurantsapi.domain.Restaurant;
import com.svalero.toprestaurantsapi.domain.dto.RestaurantDTO;
import com.svalero.toprestaurantsapi.exception.AddressAlreadyInARestaurantException;
import com.svalero.toprestaurantsapi.exception.AddressNotFoundException;
import com.svalero.toprestaurantsapi.exception.ErrorMessage;
import com.svalero.toprestaurantsapi.exception.RestaurantNotFoundException;
import com.svalero.toprestaurantsapi.service.RestaurantService;

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
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;
    private final Logger logger = LoggerFactory.getLogger(ReserveController.class);

    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getRestaurants(@RequestParam(name = "veganmenu", defaultValue = "") String veganmenu) {
        logger.debug("begin getRestaurants");
        if (veganmenu.equals("")) {
            logger.debug("end getRestaurants");
            return ResponseEntity.ok(restaurantService.findAll());
        } else {
            boolean veganMenu = Boolean.parseBoolean(veganmenu);
            logger.debug("end getRestaurants");
            return ResponseEntity.ok(restaurantService.findByVeganMenu(veganMenu));
        }
    }

    @GetMapping("/restaurants/{id}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable long id) throws RestaurantNotFoundException {
        logger.debug("begin getRestaurant");
        Restaurant restaurant = restaurantService.findById(id);
        logger.debug("end getRestaurant");
        return ResponseEntity.ok(restaurant);
    }

    @PostMapping("/restaurants")
    public ResponseEntity<Restaurant> addRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO) throws AddressNotFoundException, AddressAlreadyInARestaurantException {
        logger.debug("begin addRestaurant");
        Restaurant newRestaurant = restaurantService.addRestaurant(restaurantDTO);
        logger.debug("end addRestaurant");
        return ResponseEntity.status(HttpStatus.CREATED).body(newRestaurant);

    }

    @DeleteMapping ("/restaurants/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable long id) throws RestaurantNotFoundException {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/restaurants/{id}")
    public ResponseEntity<Restaurant> modifyRestaurant(@PathVariable long id, @RequestBody Restaurant restaurant) throws RestaurantNotFoundException {
        logger.debug("begin modifyRestaurant");
        Restaurant modifiedRestaurant = restaurantService.modifyRestaurant(id, restaurant);
        logger.debug("end modifyRestaurant");
        return ResponseEntity.status(HttpStatus.OK).body(modifiedRestaurant);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleRestaurantNotFoundException(RestaurantNotFoundException rnfe) {
        logger.error(rnfe.getMessage(), rnfe);
        ErrorMessage errorMessage = new ErrorMessage(404, rnfe.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleAddressNotFoundException(AddressNotFoundException anfe) {
        logger.error(anfe.getMessage(), anfe);
        ErrorMessage errorMessage = new ErrorMessage(404, anfe.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AddressAlreadyInARestaurantException.class)
    public ResponseEntity<ErrorMessage> handleAddressAlreadyInARestaurantException(AddressAlreadyInARestaurantException aaiare) {
        logger.error(aaiare.getMessage(), aaiare);
        ErrorMessage errorMessage = new ErrorMessage(400, aaiare.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<ErrorMessage> handleException(Exception exception) {
        logger.error(exception.getMessage(), exception);
        ErrorMessage errorMessage = new ErrorMessage(500, "Internal Server Error");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
