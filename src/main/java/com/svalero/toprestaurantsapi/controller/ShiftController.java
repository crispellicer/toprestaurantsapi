package com.svalero.toprestaurantsapi.controller;

import com.svalero.toprestaurantsapi.domain.Shift;
import com.svalero.toprestaurantsapi.exception.ErrorMessage;
import com.svalero.toprestaurantsapi.exception.ShiftNotFoundException;
import com.svalero.toprestaurantsapi.service.ShiftService;
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
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    @GetMapping("/shifts")
    public ResponseEntity<List<Shift>> getShift() {
        return ResponseEntity.ok(shiftService.findAll());
    }

    @GetMapping("/shifts/{id}")
    public ResponseEntity<Shift> getShift(@PathVariable long id) throws ShiftNotFoundException {
        Shift shift = shiftService.findById(id);
        return ResponseEntity.ok(shift);
    }

    @PostMapping("/shifts")
    public ResponseEntity<Shift> addShift(@Valid @RequestBody Shift shift) {
        Shift newShift = shiftService.addShift(shift);
        return ResponseEntity.status(HttpStatus.CREATED).body(newShift);
    }

    @DeleteMapping("/shifts/{id}")
    public ResponseEntity<Void> deleteShift(@PathVariable long id) throws ShiftNotFoundException {
        shiftService.deleteShift(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/shift/{id}")
    public ResponseEntity<Shift> modifyShift(@PathVariable long id, @RequestBody Shift shift) throws ShiftNotFoundException{
        Shift modifiedShift = shiftService.modifyShift(id, shift);
        return ResponseEntity.status(HttpStatus.OK).body(modifiedShift);
    }

    @ExceptionHandler(ShiftNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleShiftNotFoundException(ShiftNotFoundException snfe) {
        ErrorMessage errorMessage = new ErrorMessage(404, snfe.getMessage());
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

