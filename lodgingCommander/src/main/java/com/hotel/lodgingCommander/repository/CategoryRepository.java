package com.hotel.lodgingCommander.repository;

import com.hotel.lodgingCommander.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
