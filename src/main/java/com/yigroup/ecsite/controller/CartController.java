package com.yigroup.ecsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yigroup.ecsite.entity.Cart;
import com.yigroup.ecsite.entity.User;
import com.yigroup.ecsite.exception.InsufficientStockException;
import com.yigroup.ecsite.service.CartService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public String addToCart(@RequestParam int productId, @RequestParam int stockId,
            @RequestParam int selectedQuantity, RedirectAttributes redirectAttributes) {
        try {
            cartService.addToCart(productId, stockId, selectedQuantity);
            redirectAttributes.addFlashAttribute("message", "商品がカートに追加されました。");
            return "redirect:/cart";
        } catch (InsufficientStockException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/product/" + productId;
        }
    }

    @GetMapping
    public String viewCart(Model model) {
        Cart cart = new Cart();
        try {
            // ログインユーザーのカートを取得
            cart = cartService.getCartForCurrentUser();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }

        // カートのアイテムをモデルに追加
        model.addAttribute("cart", cart);
        return "cart/detail"; // カート詳細ページのテンプレート名
    }

    @GetMapping("/checkout")
    public String checkout(RedirectAttributes redirectAttributes, HttpSession session) {
        // ユーザーがログインしているかどうかを確認
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "購入手続きにはログインが必要です。");
            return "redirect:/register";
        }

        // ログインしている場合、購入手続きへ進む
        return "checkout/confirmation";
    }
}
