package com.svalero.toprestaurantsapi.service;

import com.svalero.toprestaurantsapi.domain.Customer;
import com.svalero.toprestaurantsapi.exception.CustomerNotFoundException;
import com.svalero.toprestaurantsapi.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(long id) throws CustomerNotFoundException {
        return customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public List<Customer> findByName(String name) {
        return customerRepository.findByName(name);
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(long id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
        customerRepository.delete(customer);
    }

    @Override
    public Customer modifyCustomer(long id, Customer newCustomer) throws CustomerNotFoundException {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
        existingCustomer.setName(newCustomer.getName());
        existingCustomer.setSurname(newCustomer.getSurname());
        existingCustomer.setTelephone(newCustomer.getTelephone());
        existingCustomer.setBirthDate(newCustomer.getBirthDate());
        existingCustomer.setVip(newCustomer.isVip());

        return customerRepository.save(existingCustomer);
    }
}
