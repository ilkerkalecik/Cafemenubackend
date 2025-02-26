package com.example.cafemenu.service;

import com.example.cafemenu.entity.Category;
import com.example.cafemenu.entity.Product;
import com.example.cafemenu.repository.CategoryRepository;
import com.example.cafemenu.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    // Tüm kategorileri getir
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // ID'ye göre kategori getir
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    // Kategori güncelleme
    public Category updateCategory(Long id, Category updatedCategory) {
        return categoryRepository.findById(id).map(category -> {
            category.setName(updatedCategory.getName());
            return categoryRepository.save(category);
        }).orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    // Yeni kategori ekle
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    // ID'ye göre kategori sil
    public void deleteCategory(Long id) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();

            // Kategoriye ait tüm ürünleri al
            List<Product> products = category.getProducts();

            for (Product product : products) {
                // Ürünün fotoğrafını sil
                String imageUrl = product.getImageUrl();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                    Path filePath = Paths.get("uploads", fileName);
                    try {
                        Files.deleteIfExists(filePath);
                    } catch (IOException e) {
                        System.err.println("Error deleting image file: " + e.getMessage());
                    }
                }
            }

            // Veritabanından ürünleri sil
            productRepository.deleteAll(products);

            // Kategoriyi veritabanından sil
            categoryRepository.deleteById(id);
        }
    }

    // İsme göre kategori bul
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }
    
    public void updateCategoryOrder(List<Category> categories) {
        for (Category category : categories) {
            categoryRepository.findById(category.getId()).ifPresent(existingCategory -> {
                existingCategory.setCategoryOrder(category.getCategoryOrder());
                categoryRepository.save(existingCategory);
            });
        }
    }
    
}
