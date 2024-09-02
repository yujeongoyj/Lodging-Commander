package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.model.CategoryModel;
import com.hotel.lodgingCommander.service.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private CategoryServiceImpl service;

    @PostMapping
    public ResponseEntity<?> saveCategory(@RequestBody CategoryModel categoryDTO) {
        return ResponseEntity.ok(service.save(categoryDTO));
    }

    @GetMapping
    public ResponseEntity<List<?>> getAllCategories() {
        return ResponseEntity.ok(service.getAllCategories());
    }
}
