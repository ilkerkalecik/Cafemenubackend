package com.example.cafemenu.controller;

import com.example.cafemenu.entity.Product;
import com.example.cafemenu.service.ProductService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;



@RestController
@RequestMapping("/api/product")

public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Tüm ürünleri getir
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // ID'ye göre ürün getir
    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // Yeni ürün ekle
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }
 // Ürün güncelleme
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productService.updateProduct(id, updatedProduct);
    }


    // Ürün silme
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            // Ürün fotoğrafını dosya sisteminden sil
            String imageUrl = product.get().getImageUrl();
            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1); // URL'den dosya adını çıkar

            Path filePath = Paths.get("/uploads", fileName);  // /var/www/uploads
            try {
                Files.deleteIfExists(filePath); // Fotoğrafı sil
            } catch (IOException e) {
                System.err.println("Error deleting image file: " + e.getMessage());
            }

            // Ürünü veritabanından sil
            productService.deleteProduct(id);
        }
    }


    // Kategoriye göre ürünleri getir
    @GetMapping("/category/{categoryId}")
    public List<Product> getProductsByCategory(@PathVariable Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }
    @PostMapping("/upload-image")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        // Define the file name with timestamp to avoid overwriting
        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        
        // Define the path for the upload directory
        Path uploadDir = Paths.get("uploads"); ///var/www/uploads

        
        // Create the directory if it doesn't exist
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        
        // Define the path where the file will be saved
        Path filePath = uploadDir.resolve(fileName);
        
        // Save the file to the specified location
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Generate the URL for accessing the file
        String imageUrl = "http://localhost:8080/uploads/" + fileName;  //http://www.ilkerkalecikmenu.shop/uploads/


        // Prepare the response
        Map<String, String> response = new HashMap<>();
        response.put("url", imageUrl);

        return ResponseEntity.ok(response);
    }

}
