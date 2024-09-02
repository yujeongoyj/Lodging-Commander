package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.model.cart.CartRequestModel;

import java.util.Map;

public interface CartService {

    Map<?, ?> getCartsByUserId(Long userId);

    Boolean delete(Map<?, ?> request);

    Boolean insert(CartRequestModel requestDTO);
}
