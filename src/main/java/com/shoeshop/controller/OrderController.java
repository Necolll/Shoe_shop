package com.shoeshop.controller;

import com.shoeshop.model.CheckoutForm;
import com.shoeshop.model.CustomerOrder;
import com.shoeshop.repository.OrderRepository;
import com.shoeshop.service.CartService;
import com.shoeshop.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class OrderController {

    private final CartService cart;
    private final OrderService orderService;
    private final OrderRepository orders;

    public OrderController(CartService cart, OrderService orderService, OrderRepository orders) {
        this.cart = cart;
        this.orderService = orderService;
        this.orders = orders;
    }

    @GetMapping("/checkout")
    public String form(Model model) {
        if (cart.isEmpty()) return "redirect:/cart";
        model.addAttribute("checkoutForm", new CheckoutForm());
        model.addAttribute("total", cart.total());
        return "checkout";
    }

    @PostMapping("/checkout")
    public String submit(@Valid @ModelAttribute CheckoutForm checkoutForm,
                         BindingResult result, Model model) {
        if (cart.isEmpty()) return "redirect:/cart";
        if (result.hasErrors()) {
            model.addAttribute("total", cart.total());
            return "checkout";
        }
        CustomerOrder order = orderService.create(checkoutForm, cart);
        cart.clear();
        return "redirect:/order/" + order.getId();
    }

    @GetMapping("/order/{id}")
    public String success(@PathVariable Long id, Model model) {
        CustomerOrder order = orders.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Заказ не найден"));
        model.addAttribute("order", order);
        return "order-success";
    }
}
