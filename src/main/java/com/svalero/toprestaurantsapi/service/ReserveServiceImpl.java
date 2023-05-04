package com.svalero.toprestaurantsapi.service;

import com.svalero.toprestaurantsapi.domain.Customer;
import com.svalero.toprestaurantsapi.domain.Reserve;
import com.svalero.toprestaurantsapi.domain.Restaurant;
import com.svalero.toprestaurantsapi.domain.Shift;
import com.svalero.toprestaurantsapi.domain.dto.ReserveDTO;
import com.svalero.toprestaurantsapi.exception.CustomerNotFoundException;
import com.svalero.toprestaurantsapi.exception.ReserveNotFoundException;
import com.svalero.toprestaurantsapi.exception.RestaurantNotFoundException;
import com.svalero.toprestaurantsapi.exception.ShiftNotFoundException;
import com.svalero.toprestaurantsapi.repository.CustomerRepository;
import com.svalero.toprestaurantsapi.repository.ReserveRepository;
import com.svalero.toprestaurantsapi.repository.RestaurantRepository;
import com.svalero.toprestaurantsapi.repository.ShiftRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Reserve> findAll() {
        return reserveRepository.findAll();
    }

    //@Override
    //public Reserve findByCustomerName(String customerName) {
    //    return reserveRepository.findByCustomerName(customerName);
  //  }

    @Override
    public Reserve findById(long id) throws ReserveNotFoundException {
        return reserveRepository.findById(id)
                .orElseThrow(ReserveNotFoundException::new);
    }

    @Override
    public List<Reserve> findByIsPaid(boolean isPaid) {
        return reserveRepository.findByIsPaid(isPaid);
    }

    @Override
    public Reserve addReserve(ReserveDTO reserveDTO) throws RestaurantNotFoundException, CustomerNotFoundException, ShiftNotFoundException {
        Reserve newReserve = new Reserve();
        modelMapper.map(reserveDTO, newReserve);

        Restaurant restaurant = restaurantRepository.findById(reserveDTO.getRestaurant())
                .orElseThrow(RestaurantNotFoundException::new);
        newReserve.setRestaurant(restaurant);

        Customer customer = customerRepository.findById(reserveDTO.getCustomer())
                .orElseThrow(CustomerNotFoundException::new);
        newReserve.setCustomer(customer);

        Shift shift = shiftRepository.findById(reserveDTO.getShift())
                .orElseThrow(CustomerNotFoundException::new);
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
}