package com.shoeshop.service;

import com.shoeshop.model.CartItem;
import com.shoeshop.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Корзина хранится в HTTP-сессии пользователя. */
@Component("cartService")
@SessionScope
public class CartService {

    private final List<CartItem> items = new ArrayList<>();

    public void add(Product product, String size, int quantity) {
        for (CartItem item : items) {
            if (item.getProductId().equals(product.getId()) && item.getSize().equals(size)) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(product.getId(), product.getName(), product.getPrice(), size, quantity));
    }

    public void remove(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }

    public void clear() {
        items.clear();
    }

    public List<CartItem> getItems() {
        return List.copyOf(items);
    }

    public int count() {
        return items.stream().mapToInt(CartItem::getQuantity).sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public BigDecimal total() {
        return items.stream().map(CartItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
