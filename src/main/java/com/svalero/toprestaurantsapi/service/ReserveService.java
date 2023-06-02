package com.svalero.toprestaurantsapi.service;


import com.svalero.toprestaurantsapi.domain.Reserve;;
import com.svalero.toprestaurantsapi.domain.Restaurant;
import com.svalero.toprestaurantsapi.domain.dto.ReserveInDTO;
import com.svalero.toprestaurantsapi.domain.dto.ReserveOutDTO;
import com.svalero.toprestaurantsapi.exception.CustomerNotFoundException;
import com.svalero.toprestaurantsapi.exception.ReserveNotFoundException;
import com.svalero.toprestaurantsapi.exception.RestaurantNotFoundException;
import com.svalero.toprestaurantsapi.exception.ShiftNotFoundException;

import java.util.List;

public interface ReserveService {

    List<ReserveOutDTO> findAll();
    List<ReserveOutDTO> findAllByIsPaid(boolean isPaid);
    Reserve findById(long id) throws ReserveNotFoundException;
    List<Reserve> findByRestaurant(Restaurant restaurant);
    Reserve addReserve (ReserveInDTO reserveInDTO, long restaurantId) throws RestaurantNotFoundException, CustomerNotFoundException, ShiftNotFoundException;
    void deleteReserve (long id) throws ReserveNotFoundException;
    Reserve modifyReserve(long id, Reserve newReserve) throws ReserveNotFoundException;

}
