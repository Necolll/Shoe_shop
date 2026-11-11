package com.shoeshop.service;

import com.shoeshop.model.*;
import com.shoeshop.repository.OrderRepository;
import com.shoeshop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class OrderService {

    private final OrderRepository orders;
    private final ProductRepository products;

    public OrderService(OrderRepository orders, ProductRepository products) {
        this.orders = orders;
        this.products = products;
    }

    @Transactional
    public CustomerOrder create(CheckoutForm form, CartService cart) {
        CustomerOrder order = new CustomerOrder();
        order.setCustomerName(form.getCustomerName());
        order.setEmail(form.getEmail());
        order.setPhone(form.getPhone());
        order.setAddress(form.getAddress());

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem ci : cart.getItems()) {
            // цену берём из БД, а не из сессии
            Product product = products.findById(ci.getProductId())
                    .orElseThrow(() -> new IllegalStateException("Товар не найден: " + ci.getProductId()));

            OrderItem item = new OrderItem();
            item.setProductId(product.getId());
            item.setProductName(product.getName());
            item.setSize(ci.getSize());
            item.setQuantity(ci.getQuantity());
            item.setPrice(product.getPrice());
            order.addItem(item);

            total = total.add(item.getSubtotal());
        }
        order.setTotal(total);
        return orders.save(order);
    }
}
