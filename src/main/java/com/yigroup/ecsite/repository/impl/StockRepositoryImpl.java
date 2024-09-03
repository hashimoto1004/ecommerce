package com.yigroup.ecsite.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yigroup.ecsite.entity.Stock;
import com.yigroup.ecsite.repository.StockRepository;

@Repository
public class StockRepositoryImpl implements StockRepository {

    private final JdbcTemplate jdbcTemplate;

    public StockRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Stock> findById(int id) {
        String sql = "SELECT * FROM stocks WHERE id = ?";
        return jdbcTemplate.query(sql, new Object[] { id }, new StockRowMapper())
                .stream()
                .findFirst();
    }

    private static class StockRowMapper implements RowMapper<Stock> {
        @Override
        public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {
            Stock stock = new Stock();
            stock.setId(rs.getInt("id"));
            stock.setProductId(rs.getInt("product_id"));
            stock.setSizeId(rs.getInt("size_id"));
            stock.setQuantity(rs.getInt("quantity"));
            stock.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            stock.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            return stock;
        }
    }
}
