package com.svalero.toprestaurantsapi.service;


import com.svalero.toprestaurantsapi.domain.Restaurant;
import com.svalero.toprestaurantsapi.exception.RestaurantNotFoundException;

import java.util.List;

public interface RestaurantService {

    List<Restaurant> findAll();
    List<Restaurant> findByVeganMenu(boolean veganMenu);
    Restaurant findById(long id) throws RestaurantNotFoundException;
    Restaurant addRestaurant (Restaurant restaurant);
    void deleteRestaurant (long id) throws RestaurantNotFoundException;
    Restaurant modifyRestaurant(long id, Restaurant newRestaurant) throws RestaurantNotFoundException;

}
