package com.svalero.toprestaurantsapi.service;

import com.svalero.toprestaurantsapi.domain.Shift;
import com.svalero.toprestaurantsapi.exception.ShiftNotFoundException;
import com.svalero.toprestaurantsapi.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftServiceImpl implements ShiftService{

    @Autowired
    private ShiftRepository shiftRepository;

    @Override
    public List<Shift> findAll() {
        return shiftRepository.findAll();
    }

    @Override
    public Shift findById(long id) throws ShiftNotFoundException {
        return shiftRepository.findById(id)
                .orElseThrow(ShiftNotFoundException::new);
    }

    @Override
    public Shift addShift(Shift shift) {
        return shiftRepository.save(shift);
    }

    @Override
    public void deleteShift(long id) throws ShiftNotFoundException {
        Shift shift = shiftRepository.findById(id)
                .orElseThrow(ShiftNotFoundException::new);
        shiftRepository.delete(shift);
    }

    @Override
    public Shift modifyShift(long id, Shift newShift) throws ShiftNotFoundException {
        return null;
    }
}
