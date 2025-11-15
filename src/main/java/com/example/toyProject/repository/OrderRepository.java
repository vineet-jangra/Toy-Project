package com.example.toyProject.repository;

import com.example.toyProject.DTO.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int insertOrder(Order order) {
        final String query = "INSERT INTO Orders(USERNAME, ADDRESS, TOTALAMOUNT) VALUES(?,?,?)"; //return id from here
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getUsername());
            ps.setString(2, order.getAddress());
            ps.setDouble(3, order.getTotalAmount());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }
    public int[] createMapping(Integer orderId, Map<Integer, Integer> productIdToQuantityMap) {
        final String sql = "INSERT INTO Orders_Products(ORDER_ID, PRODUCT_ID, PRODUCT_QUANTITY) VALUES (?, ?, ?)";
        List<Object[]> batchArgs = productIdToQuantityMap.entrySet().stream().map(entry ->
            new Object[] {entry.getKey(), entry.getValue()}
                ).toList();
        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, orderId);
                ps.setInt(2, (Integer) batchArgs.get(i)[0]);
                ps.setInt(3, (Integer) batchArgs.get(i)[1]);
            }
            @Override
            public int getBatchSize() {
                return batchArgs.size();
            }
        });
    }

    public Order getOrderBasedOnId(Integer id) {
        String sql = "SELECT * FROM Orders WHERE ID = ?";
        Object[] args = new Object[]{id};
        int[] argTypes = new int[] {Types.INTEGER};
        return jdbcTemplate.queryForObject(sql, args, argTypes, new BeanPropertyRowMapper<>(Order.class));
    }

    public Map<Integer, Integer> getProductIdToQuantityMapping(Integer id) {
        String sql = "select product_id, product_quantity from Orders_Products where order_id = ?";
        return jdbcTemplate.query(sql,  rs -> {
            Map<Integer, Integer> map = new HashMap<>();
            while (rs.next()) {
                map.put(rs.getInt("product_id"), rs.getInt("product_quantity"));
            }
            return map;
        }, id);
    }
}
