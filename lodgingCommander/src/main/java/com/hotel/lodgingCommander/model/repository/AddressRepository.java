package com.hotel.lodgingCommander.model.repository;

import com.hotel.lodgingCommander.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
