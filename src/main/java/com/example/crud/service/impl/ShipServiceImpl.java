package com.example.crud.service.impl;

import com.example.crud.entity.Address;
import com.example.crud.repository.ShipRepository;
import com.example.crud.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShipServiceImpl implements ShipService {

    private ShipRepository shipRepository;

    @Autowired
    public ShipServiceImpl(ShipRepository shipRepository){
        this.shipRepository= shipRepository;
    }

    @Override
    public void save(Address address) {
        shipRepository.save(address);
    }

    @Override
    public void delete(Address address) {
        shipRepository.delete(address);
    }

    @Override
    public Address getAddress(long addressId) {
        return shipRepository.findById(addressId).get();
    }
}
