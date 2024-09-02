package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.model.CategoryModel;

import java.util.List;

public interface CategoryService {

    Boolean save(CategoryModel categoryDTO);

    List<?> getAllCategories();
}
