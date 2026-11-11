package com.shoeshop.controller;

import com.shoeshop.model.Product;
import com.shoeshop.repository.ProductRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Простое JSON API каталога: GET /api/products */
@RestController
@RequestMapping("/api")
public class ApiController {

    private final ProductRepository products;

    public ApiController(ProductRepository products) {
        this.products = products;
    }

    @GetMapping("/products")
    public List<Product> list() {
        return products.findAll();
    }
}
