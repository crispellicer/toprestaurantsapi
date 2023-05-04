package com.svalero.toprestaurantsapi.service;

import com.svalero.toprestaurantsapi.domain.Restaurant;
import com.svalero.toprestaurantsapi.exception.RestaurantNotFoundException;
import com.svalero.toprestaurantsapi.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> findByVeganMenu(boolean veganMenu) {
        return restaurantRepository.findByVeganMenu(veganMenu);
    }

    @Override
    public Restaurant findById(long id) throws RestaurantNotFoundException {
        return restaurantRepository.findById(id)
                .orElseThrow(RestaurantNotFoundException::new);
    }

    @Override
    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(long id) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(RestaurantNotFoundException::new);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public Restaurant modifyRestaurant(long id, Restaurant newRestaurant) throws RestaurantNotFoundException {
        Restaurant existingRestaurant = restaurantRepository.findById(id)
                .orElseThrow(RestaurantNotFoundException::new);
        existingRestaurant.setName(newRestaurant.getName());
        existingRestaurant.setTimetable(newRestaurant.getTimetable());
        existingRestaurant.setReservePrice(newRestaurant.getReservePrice());
        existingRestaurant.setVeganMenu(newRestaurant.isVeganMenu());
        existingRestaurant.setWebsite(newRestaurant.getWebsite());
        return restaurantRepository.save(existingRestaurant);
    }
}
