package com.yigroup.ecsite.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yigroup.ecsite.entity.Cart;
import com.yigroup.ecsite.entity.CartItem;
import com.yigroup.ecsite.entity.CartItemForView;
import com.yigroup.ecsite.repository.CartRepository;

@Repository
public class CartRepositoryImpl implements CartRepository {

    private final JdbcTemplate jdbcTemplate;

    public CartRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Cart> findByUserId(int userId) {
        String sql = "SELECT * FROM carts WHERE user_id = ?";
        List<Cart> carts = jdbcTemplate.query(sql, new Object[] { userId }, new CartRowMapper());

        if (carts.isEmpty()) {
            return Optional.empty();
        } else {
            Cart cart = carts.get(0);
            cart.setItems(findCartItems(cart.getId()));
            return Optional.of(cart);
        }
    }

    @Override
    public Optional<Cart> findCartItemsByUserId(int userId) {
        String sql = """
                SELECT
                 	c.id as cart_id,
                 	c.user_id as user_id,
                 	ci.id as cart_item_id,
                 	ci.quantity as quantity,
                 	p.name as product_name,
                 	p.price as price,
                 	si.value as size,
                 	img.image_url as image_url
                 FROM
                 	carts c
                 JOIN
                 	cart_items ci ON c.id = ci.cart_id
                 JOIN
                 	products p ON ci.product_id = p.id
                 JOIN
                 	stocks st ON ci.stock_id = st.id
                 JOIN
                 	sizes si ON st.size_id = si.id
                 LEFT JOIN
                 	images img ON p.id = img.product_id
                 WHERE
                 	c.user_id = ?;
                     """;

        // クエリの実行とマッピング
        Cart cart = jdbcTemplate.query(sql, new Object[] { userId }, new CartResultSetExtractor());
        return Optional.ofNullable(cart); // Optionalでラップして返す
    }

    @Override
    public void save(Cart cart) {
        if (cart.getId() == 0) {
            // 新規のカートの場合はINSERT
            String sql = "INSERT INTO carts (user_id) VALUES (?) RETURNING id";
            int cartId = jdbcTemplate.queryForObject(sql,
                    new Object[] { cart.getUserId() }, Integer.class);
            cart.setId(cartId);
        } else {
            // 既存のカートの場合はUPDATE
            String sql = "UPDATE carts SET updated_at = ? WHERE id = ?";
            jdbcTemplate.update(sql, cart.getUpdatedAt(), cart.getId());
        }

        // カートアイテムを保存
        saveCartItems(cart.getId(), cart.getItems());
    }

    @Override
    public void delete(Cart cart) {
        String sql = "DELETE FROM carts WHERE id = ?";
        jdbcTemplate.update(sql, cart.getId());

        deleteCartItems(cart.getId());
    }

    private List<CartItem> findCartItems(int cartId) {
        String sql = "SELECT * FROM cart_items WHERE cart_id = ?";
        return jdbcTemplate.query(sql, new Object[] { cartId }, new CartItemRowMapper());
    }

    private void saveCartItems(int cartId, List<CartItem> items) {
        deleteCartItems(cartId); // 一旦既存のアイテムを削除してから再挿入

        String sql = "INSERT INTO cart_items (cart_id, product_id, stock_id, quantity, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        for (CartItem item : items) {
            jdbcTemplate.update(sql, cartId, item.getProductId(), item.getStockId(), item.getQuantity(),
                    item.getCreatedAt(), item.getUpdatedAt());
        }
    }

    private void deleteCartItems(int cartId) {
        String sql = "DELETE FROM cart_items WHERE cart_id = ?";
        jdbcTemplate.update(sql, cartId);
    }

    // RowMapperクラス
    private static class CartRowMapper implements RowMapper<Cart> {
        @Override
        public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
            Cart cart = new Cart();
            cart.setId(rs.getInt("id"));
            cart.setUserId(rs.getInt("user_id"));
            cart.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            cart.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            return cart;
        }
    }

    private static class CartItemRowMapper implements RowMapper<CartItem> {
        @Override
        public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            CartItem item = new CartItem();
            item.setId(rs.getInt("id"));
            item.setCartId(rs.getInt("cart_id"));
            item.setProductId(rs.getInt("product_id"));
            item.setStockId(rs.getInt("stock_id"));
            item.setQuantity(rs.getInt("quantity"));
            item.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            item.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            return item;
        }
    }

    public class CartResultSetExtractor implements ResultSetExtractor<Cart> {
        @Override
        public Cart extractData(ResultSet rs) throws SQLException, DataAccessException {
            Cart cart = null;
            Map<Integer, CartItemForView> cartItemMap = new HashMap<>();

            while (rs.next()) {
                if (cart == null) {
                    cart = new Cart();
                    cart.setId(rs.getInt("cart_id"));
                    cart.setUserId(rs.getInt("user_id"));
                }

                int cartItemId = rs.getInt("cart_item_id");
                CartItemForView cartItem = cartItemMap.get(cartItemId);

                if (cartItem == null) {
                    cartItem = new CartItemForView();
                    cartItem.setId(cartItemId);
                    cartItem.setQuantity(rs.getInt("quantity"));
                    cartItem.setProductName(rs.getString("product_name"));
                    cartItem.setPrice(rs.getInt("price"));
                    cartItem.setSize(rs.getDouble("size"));
                    cartItem.setImageUrl(rs.getString("image_url"));
                    cartItem.setSubTotalPrice(cartItem.getPrice() * cartItem.getQuantity());

                    cartItemMap.put(cartItemId, cartItem);
                }
            }

            if (cart != null) {
                cart.setItemsForView(new ArrayList<>(cartItemMap.values()));
            }

            return cart;
        }
    }

}
