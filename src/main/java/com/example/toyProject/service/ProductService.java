package com.example.toyProject.service;

import com.example.toyProject.DTO.Product;
import com.example.toyProject.repository.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private RedisTemplate<String, List<Product>> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return returnViaCache();
    }

    public List<Product> returnViaCache() {

        List<Product> products = (List<Product>) redisTemplate.opsForValue().get("products");
        if (products == null || products.isEmpty()) {
            products =  productRepository.getAllProducts();
            redisTemplate.opsForValue().set("products", products);
        }
        else {
            System.out.println("From cache");
            products = objectMapper.convertValue(products, new TypeReference<List<Product>>() {});
        }
        return products;
    }

    public String createProduct(Product product) {
        return productRepository.createProduct(product) > 0 ? "success" : "failure";
    }

    public String deleteProduct(Integer id) {
        return productRepository.deleteProduct(id) > 0 ? "success" : "failure";
    }

    public String updateProduct(Product product) {
        return productRepository.updateProduct(product) > 0 ? "success" : "failure";
    }
}
