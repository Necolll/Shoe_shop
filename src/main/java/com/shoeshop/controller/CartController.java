package com.shoeshop.controller;

import com.shoeshop.model.Product;
import com.shoeshop.repository.ProductRepository;
import com.shoeshop.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class CartController {

    private final CartService cart;
    private final ProductRepository products;

    public CartController(CartService cart, ProductRepository products) {
        this.cart = cart;
        this.products = products;
    }

    @GetMapping("/cart")
    public String view(Model model) {
        model.addAttribute("items", cart.getItems());
        model.addAttribute("total", cart.total());
        return "cart";
    }

    @PostMapping("/cart/add")
    public String add(@RequestParam Long productId,
                      @RequestParam String size,
                      @RequestParam(defaultValue = "1") int quantity) {
        Product product = products.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));
        if (!product.getSizeList().contains(size)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Недоступный размер");
        }
        cart.add(product, size, Math.max(1, Math.min(quantity, 10)));
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String remove(@RequestParam int index) {
        cart.remove(index);
        return "redirect:/cart";
    }
}
