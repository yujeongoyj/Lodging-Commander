package com.hotel.lodgingCommander.repository;

import com.hotel.lodgingCommander.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapRepository extends JpaRepository<Address, Long> {
        //List<Address> findByHotel_Id(Long id);
}
