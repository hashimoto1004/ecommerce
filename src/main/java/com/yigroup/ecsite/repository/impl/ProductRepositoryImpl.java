package com.yigroup.ecsite.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yigroup.ecsite.entity.Image;
import com.yigroup.ecsite.entity.Product;
import com.yigroup.ecsite.entity.Stock;
import com.yigroup.ecsite.repository.ProductRepository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT p.*, i.image_url, i.alt_text "
                + "FROM products p "
                + "LEFT JOIN images i ON p.id = i.product_id";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    @Override
    public Optional<Product> findById(int id) {
        String sql = "SELECT p.*, i.image_url, i.alt_text, st.size_id, st.quantity "
                + ", st.id as stock_id, si.value "
                + "FROM products p "
                + "LEFT JOIN images i ON p.id = i.product_id "
                + "LEFT JOIN stocks st ON p.id = st.product_id "
                + "LEFT JOIN sizes si ON st.size_id = si.id "
                + "WHERE p.id = ?";

        return jdbcTemplate.query(sql, new Object[] { id }, new ProductRowMapperOfDetail())
                .stream()
                .findFirst();
    }

    @Override
    public void save(Product product) {
        if (product.getId() == 0) {
            // 新規作成の場合
            String sql = "INSERT INTO products (name, description, created_at, updated_at) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, product.getName(), product.getDescription(),
                    Timestamp.valueOf(product.getCreatedAt()), Timestamp.valueOf(product.getUpdatedAt()));
        } else {
            // 更新の場合
            String sql = "UPDATE products SET name = ?, description = ?, updated_at = ? WHERE id = ?";
            jdbcTemplate.update(sql, product.getName(), product.getDescription(),
                    Timestamp.valueOf(product.getUpdatedAt()), product.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getInt("price"));
            product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            product.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

            // 画像の処理
            Image image = new Image();
            image.setImageUrl(rs.getString("image_url"));
            image.setAltText(rs.getString("alt_text"));

            // 必要に応じて、リストに追加したり、セットしたりする
            product.setImages(List.of(image));

            return product;
        }
    }

    private static class ProductRowMapperOfDetail implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            List<Image> images = new ArrayList<>();
            List<Stock> stocks = new ArrayList<>();

            do {
                // 初回行でProductの基本情報をセット
                if (rowNum == 0) {
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setPrice(rs.getInt("price"));
                    product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    product.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                }

                // 画像の処理
                Image image = new Image();
                image.setImageUrl(rs.getString("image_url"));
                image.setAltText(rs.getString("alt_text"));
                images.add(image);

                // バリエーションの処理
                Stock stock = new Stock();
                stock.setId(rs.getInt("stock_id"));
                stock.setSizeValue(rs.getInt("value"));
                stock.setQuantity(rs.getInt("quantity"));
                stocks.add(stock);

            } while (rs.next());

            // 最終的にリストをセット
            product.setImages(images);
            product.setStocks(stocks);

            return product;
        }

    }
}
