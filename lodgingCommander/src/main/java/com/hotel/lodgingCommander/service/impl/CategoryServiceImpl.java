package com.hotel.lodgingCommander.service.impl;

import com.hotel.lodgingCommander.model.CategoryModel;
import com.hotel.lodgingCommander.model.entity.Category;
import com.hotel.lodgingCommander.model.repository.CategoryRepository;
import com.hotel.lodgingCommander.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public List<?> getAllCategories() {
        var categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> new CategoryModel(category.getId(), category.getName()))
                .collect(Collectors.toList());

    }


    @Transactional
    public Boolean save(CategoryModel categoryDTO) {
        var category = Category.builder()
                .name(categoryDTO.getName())
                .build();
        return categoryRepository.save(category) != null ? true : false;
    }
}
