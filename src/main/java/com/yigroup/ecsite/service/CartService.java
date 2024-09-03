package com.yigroup.ecsite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yigroup.ecsite.entity.Cart;
import com.yigroup.ecsite.entity.CartItemForView;
import com.yigroup.ecsite.entity.Stock;
import com.yigroup.ecsite.exception.InsufficientStockException;
import com.yigroup.ecsite.repository.CartRepository;
import com.yigroup.ecsite.repository.StockRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private StockRepository stockRepository;

    public Cart getCartForCurrentUser() {
        // 現在のログインユーザーIDを取得（具体的な実装は認証方式による）
        int userId = getCurrentUserId();

        // ユーザーのカートを取得、存在しない場合は新規作成

        Cart cart = cartRepository.findCartItemsByUserId(userId)
                .orElseGet(() -> null);

        if (cart == null) {
            return null;
        }

        calculateTotalPrice(cart);

        return cart;
    }

    public void addToCart(int productId, int stockId, int quantity) {
        Stock stock = stockRepository.findById(stockId).orElse(null);

        if (stock == null || stock.getQuantity() < quantity) {
            throw new InsufficientStockException("在庫が足りません。");
        }

        // ユーザーのカートを取得または新規作成
        Cart cart = cartRepository.findByUserId(getCurrentUserId()).orElse(new Cart());

        // 削除予定
        if (cart.getUserId() == 0) {
            cart.setUserId(getCurrentUserId());
        }

        cart.addItem(productId, stockId, quantity);
        cartRepository.save(cart);

        return;
    }

    private int getCurrentUserId() {
        // ログイン中のユーザーIDを取得するロジックを実装
        return 1; // 仮のユーザーIDを返す
    }

    private void calculateTotalPrice(Cart cart) {
        int totalPrice = 0;
        for (CartItemForView item : cart.getItemsForView()) {
            totalPrice += item.getSubTotalPrice();
        }

        cart.setTotalPrice(totalPrice);
    }

    // private int calculateTotalPrice(Cart cart) {
    // int totalPrice = 0;
    // for (CartItemForView item : cart.getItemsForView()) {
    // totalPrice += item.getSubTotalPrice();
    // }

    // return totalPrice;
    // }
}
