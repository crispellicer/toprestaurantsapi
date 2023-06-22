package com.svalero.toprestaurantsapi.service;


import com.svalero.toprestaurantsapi.domain.Restaurant;
import com.svalero.toprestaurantsapi.domain.dto.RestaurantDTO;
import com.svalero.toprestaurantsapi.exception.AddressAlreadyInARestaurantException;
import com.svalero.toprestaurantsapi.exception.AddressNotFoundException;
import com.svalero.toprestaurantsapi.exception.RestaurantNotFoundException;

import java.util.List;

public interface RestaurantService {

    List<Restaurant> findAll();
    List<Restaurant> findByVeganMenu(boolean veganMenu);
    Restaurant findById(long id) throws RestaurantNotFoundException;
    Restaurant addRestaurant (RestaurantDTO restaurantDTO) throws AddressNotFoundException, AddressAlreadyInARestaurantException;
    void deleteRestaurant (long id) throws RestaurantNotFoundException;
    Restaurant modifyRestaurant(long id, Restaurant newRestaurant) throws RestaurantNotFoundException;

}
