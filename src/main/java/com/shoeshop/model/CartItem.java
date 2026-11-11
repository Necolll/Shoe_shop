package com.shoeshop.model;

import java.math.BigDecimal;

public class CartItem {
    private final Long productId;
    private final String name;
    private final BigDecimal price;
    private final String size;
    private int quantity;

    public CartItem(Long productId, String name, BigDecimal price, String size, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.size = size;
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public Long getProductId() { return productId; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public String getSize() { return size; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
