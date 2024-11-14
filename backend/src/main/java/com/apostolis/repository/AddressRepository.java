package com.apostolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apostolis.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
