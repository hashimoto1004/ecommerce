package com.yigroup.ecsite.controller;

import com.yigroup.ecsite.entity.Order;
import com.yigroup.ecsite.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String listOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order/list";
    }

    @GetMapping("/{id}")
    public String getOrder(@PathVariable int id, Model model) {
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            model.addAttribute("order", order.get());
            return "order/detail";
        } else {
            return "error/404";
        }
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute Order order) {
        orderService.createOrder(order);
        return "redirect:/orders";
    }

    @PostMapping("/cancel/{id}")
    public String cancelOrder(@PathVariable int id) {
        orderService.cancelOrder(id);
        return "redirect:/orders";
    }
}
