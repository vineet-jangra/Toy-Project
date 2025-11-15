package com.example.toyProject.repository;

import com.example.toyProject.DTO.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class ProductRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createProduct(Product product) {
        final String query = "INSERT INTO Product(NAME, CATEGORY, PRICE) VALUES(?, ?, ?)";
        Object[] values = {product.getName(), product.getCategory(), product.getPrice()};
        int[] argTypes = {Types.VARCHAR, Types.VARCHAR, Types.DOUBLE};
        return jdbcTemplate.update(query, values, argTypes);
    }

    public List<Product> getAllProducts() {
        final String query = "SELECT ID, NAME, CATEGORY, PRICE, CREATED_AT FROM Product";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Product.class));
    }

    public int deleteProduct(Integer id) {
        final String query = "DELETE FROM Product WHERE ID = ?";
        Object[] values = {id};
        int[] argTypes = {Types.INTEGER};
        return jdbcTemplate.update(query, values, argTypes);
    }

    public int updateProduct(Product product) {
        final String query = "UPDATE Product SET NAME = ?, CATEGORY = ?, PRICE = ? WHERE ID = ?";
        Object[] values = {product.getName(), product.getCategory(), product.getPrice(), product.getId()};
        int[] argTypes = {Types.VARCHAR, Types.VARCHAR, Types.DOUBLE, Types.INTEGER};
        return jdbcTemplate.update(query, values, argTypes);
    }
}
