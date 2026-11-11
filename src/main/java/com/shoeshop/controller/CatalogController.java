package com.shoeshop.controller;

import com.shoeshop.model.Product;
import com.shoeshop.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class CatalogController {

    private final ProductRepository products;

    public CatalogController(ProductRepository products) {
        this.products = products;
    }

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "") String q,
                        @RequestParam(defaultValue = "") String category,
                        Model model) {
        model.addAttribute("products", products.search(q.trim(), category));
        model.addAttribute("q", q);
        model.addAttribute("category", category);
        return "index";
    }

    @GetMapping("/product/{id}")
    public String product(@PathVariable Long id, Model model) {
        Product product = products.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));
        model.addAttribute("product", product);
        return "product";
    }
}
