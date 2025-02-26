package com.example.cafemenu.repository;

import com.example.cafemenu.entity.Category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    // Belirli bir isme göre kategori bulma
    Category findByName(String name);
    List<Category> findAllByOrderByCategoryOrderAsc();

    
}
