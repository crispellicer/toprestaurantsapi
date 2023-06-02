package com.svalero.toprestaurantsapi.service;

import com.svalero.toprestaurantsapi.domain.Address;
import com.svalero.toprestaurantsapi.exception.AddressNotFoundException;
import com.svalero.toprestaurantsapi.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public List<Address> findByCity(String city) {
        return addressRepository.findByCity(city);
    }

    @Override
    public Address findById(long id) throws AddressNotFoundException {
        return addressRepository.findById(id)
                .orElseThrow(AddressNotFoundException::new);
    }

    @Override
    public Address addAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(long id) throws AddressNotFoundException {
        Address address = addressRepository.findById(id)
                .orElseThrow(AddressNotFoundException::new);
        addressRepository.delete(address);
    }

    @Override
    public Address modifyAddress(long id, Address newAddress) throws AddressNotFoundException {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(AddressNotFoundException::new);
        existingAddress.setType(newAddress.getType());
        existingAddress.setName(newAddress.getName());
        existingAddress.setNumber(newAddress.getNumber());
        existingAddress.setPostalCode(newAddress.getPostalCode());
        existingAddress.setCity(newAddress.getCity());

        return addressRepository.save(existingAddress);
    }
}
