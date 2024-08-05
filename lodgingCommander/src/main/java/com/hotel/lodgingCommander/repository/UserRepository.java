package com.hotel.lodgingCommander.repository;

import com.hotel.lodgingCommander.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserBy(String username);

}
