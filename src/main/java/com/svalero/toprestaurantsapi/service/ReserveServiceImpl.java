package com.svalero.toprestaurantsapi.service;

import com.svalero.toprestaurantsapi.domain.Customer;
import com.svalero.toprestaurantsapi.domain.Reserve;
import com.svalero.toprestaurantsapi.domain.Restaurant;
import com.svalero.toprestaurantsapi.domain.Shift;
import com.svalero.toprestaurantsapi.domain.dto.ReserveInDTO;
import com.svalero.toprestaurantsapi.domain.dto.ReserveOutDTO;
import com.svalero.toprestaurantsapi.exception.CustomerNotFoundException;
import com.svalero.toprestaurantsapi.exception.ReserveNotFoundException;
import com.svalero.toprestaurantsapi.exception.RestaurantNotFoundException;
import com.svalero.toprestaurantsapi.exception.ShiftNotFoundException;
import com.svalero.toprestaurantsapi.repository.CustomerRepository;
import com.svalero.toprestaurantsapi.repository.ReserveRepository;
import com.svalero.toprestaurantsapi.repository.RestaurantRepository;
import com.svalero.toprestaurantsapi.repository.ShiftRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReserveServiceImpl implements ReserveService{

    @Autowired
    private ReserveRepository reserveRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ShiftRepository shiftRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ReserveOutDTO> findAll() {
        List<Reserve> reserves = reserveRepository.findAll();
        List<ReserveOutDTO> reservesOutDTO = modelMapper.map(reserves, new TypeToken<List<ReserveOutDTO>>() {}.getType());

        return reservesOutDTO;
    }

    @Override
    public List<ReserveOutDTO> findAllByIsPaid(boolean isPaid) {
        List<Reserve> reserves = reserveRepository.findAllByIsPaid(isPaid);
        List<ReserveOutDTO> reservesOutDTO = modelMapper.map(reserves, new TypeToken<List<ReserveOutDTO>>() {}.getType());

        return reservesOutDTO;
    }

    @Override
    public Reserve findById(long id) throws ReserveNotFoundException {
        return reserveRepository.findById(id)
                .orElseThrow(ReserveNotFoundException::new);
    }

    @Override
    public Reserve addReserve(ReserveInDTO reserveInDTO, long restaurantId) throws RestaurantNotFoundException, CustomerNotFoundException, ShiftNotFoundException {
        Reserve newReserve = new Reserve();
        modelMapper.map(reserveInDTO, newReserve);

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(RestaurantNotFoundException::new);
        newReserve.setRestaurant(restaurant);

        Customer customer = customerRepository.findById(reserveInDTO.getCustomer())
                .orElseThrow(CustomerNotFoundException::new);
        newReserve.setCustomer(customer);

        Shift shift = shiftRepository.findById(reserveInDTO.getShift())
                .orElseThrow(ShiftNotFoundException::new);
        newReserve.setShift(shift);

        return reserveRepository.save(newReserve);
    }

    @Override
    public void deleteReserve(long id) throws ReserveNotFoundException {
        Reserve reserve = reserveRepository.findById(id)
                .orElseThrow(ReserveNotFoundException::new);
        reserveRepository.delete(reserve);
    }

    @Override
    public Reserve modifyReserve(long id, Reserve newReserve) throws ReserveNotFoundException {
        Reserve existingReserve = reserveRepository.findById(id)
                .orElseThrow(ReserveNotFoundException::new);
        existingReserve.setPeople(newReserve.getPeople());
        existingReserve.setTables(newReserve.getTables());
        existingReserve.setReserveDate(newReserve.getReserveDate());
        existingReserve.setPaid(newReserve.isPaid());
        existingReserve.setAllergic(newReserve.isAllergic());
        return reserveRepository.save(existingReserve);
    }

    @Override
    public List<Reserve> findByRestaurant(Restaurant restaurant) {
        return reserveRepository.findByRestaurant(restaurant);
    }
}
