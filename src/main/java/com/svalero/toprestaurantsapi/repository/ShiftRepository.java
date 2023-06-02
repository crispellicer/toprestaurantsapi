package com.svalero.toprestaurantsapi.repository;

import com.svalero.toprestaurantsapi.domain.Shift;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShiftRepository extends CrudRepository<Shift, Long> {

    List<Shift> findAll();
    List<Shift> findByName(String name);
}
