package com.example.cafemenu.repository;

import com.example.cafemenu.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Belirli bir kategoriye ait ürünleri listelemek için
    List<Product> findByCategoryId(Long categoryId);
}
