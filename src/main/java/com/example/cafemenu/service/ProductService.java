package com.example.cafemenu.service;

import com.example.cafemenu.entity.Product;
import com.example.cafemenu.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Tüm ürünleri getir
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // ID'ye göre ürün getir
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Yeni ürün ekle
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
 // Ürün güncelleme
    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id).map(product -> {
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setImageUrl(updatedProduct.getImageUrl());
            product.setDescription(updatedProduct.getDescription());
            product.setCategory(updatedProduct.getCategory());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    // ID'ye göre ürün sil
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Kategoriye göre ürünleri getir
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
}
