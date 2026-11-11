package com.shoeshop.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    // men / women / kids
    @Column(nullable = false)
    private String category;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    // размеры через запятую: "39,40,41"
    @Column(nullable = false)
    private String sizes;

    private String imageUrl;

    public Product() {}

    public Product(String name, String brand, String category, String description,
                   BigDecimal price, String sizes, String imageUrl) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.description = description;
        this.price = price;
        this.sizes = sizes;
        this.imageUrl = imageUrl;
    }

    public List<String> getSizeList() {
        return Arrays.stream(sizes.split(",")).map(String::trim).toList();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getSizes() { return sizes; }
    public void setSizes(String sizes) { this.sizes = sizes; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
