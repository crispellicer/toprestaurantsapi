package com.svalero.toprestaurantsapi.service;


import com.svalero.toprestaurantsapi.domain.Reserve;;
import com.svalero.toprestaurantsapi.domain.dto.ReserveDTO;
import com.svalero.toprestaurantsapi.exception.CustomerNotFoundException;
import com.svalero.toprestaurantsapi.exception.ReserveNotFoundException;
import com.svalero.toprestaurantsapi.exception.RestaurantNotFoundException;
import com.svalero.toprestaurantsapi.exception.ShiftNotFoundException;

import java.util.List;

public interface ReserveService {

    List<Reserve> findAll();
    //Reserve findByCustomerName(String customerName);
    List<Reserve> findByIsPaid(boolean isPaid);
    Reserve findById(long id) throws ReserveNotFoundException;
    Reserve addReserve (ReserveDTO reserveDTO) throws RestaurantNotFoundException, CustomerNotFoundException, ShiftNotFoundException;
    void deleteReserve (long id) throws ReserveNotFoundException;
    Reserve modifyReserve(long id, Reserve newReserve) throws ReserveNotFoundException;

}
