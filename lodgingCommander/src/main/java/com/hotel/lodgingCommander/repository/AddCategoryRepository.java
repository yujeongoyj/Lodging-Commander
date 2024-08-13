package com.hotel.lodgingCommander.repository;

import com.hotel.lodgingCommander.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddCategoryRepository extends JpaRepository<Category, Long> {
}
