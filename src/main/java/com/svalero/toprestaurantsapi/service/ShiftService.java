package com.svalero.toprestaurantsapi.service;

import com.svalero.toprestaurantsapi.domain.Shift;
import com.svalero.toprestaurantsapi.exception.ShiftNotFoundException;

import java.util.List;

public interface ShiftService {

    List<Shift> findAll();
    Shift findById(long id) throws ShiftNotFoundException;
    List<Shift> findByName(String name);
    Shift addShift (Shift shift);
    void deleteShift (long id) throws ShiftNotFoundException;
    Shift modifyShift(long id, Shift newShift) throws ShiftNotFoundException;
}