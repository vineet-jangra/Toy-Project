package com.example.toyProject.controller;

import com.example.toyProject.DTO.Product;
import com.example.toyProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("createProduct")
    public String createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }
    @PutMapping("updateProduct")
    public String updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }
    @DeleteMapping("deleteProduct")
    public String deleteProduct(@RequestParam("id") Integer id) {
        return productService.deleteProduct(id);
    }
}
