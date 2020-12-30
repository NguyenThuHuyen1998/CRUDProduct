package com.example.crud.service;

import com.example.crud.entity.Address;

public interface ShipService {
    void save(Address address);
    void delete(Address address);
    Address getAddress(long addressId);
}
