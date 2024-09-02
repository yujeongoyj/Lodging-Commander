package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.model.entity.User;


public interface UserService {

    Boolean registerUser(User user);

    Boolean update(String email, User user);

    Boolean delete(String email);

    User getUserById(Long userId);

    Boolean emailExists(String email);

}
