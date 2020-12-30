package com.example.crud.repository;

import com.example.crud.entity.Address;
import com.example.crud.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipRepository extends PagingAndSortingRepository<Address, Long> {
}
