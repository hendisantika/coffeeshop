package com.hendisantika.coffeshop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * Product entity for coffee shop menu items
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String size;
    private boolean available;
    private String imageUrl;

    @DBRef
    private Category category;

    public Product(String name, String description, BigDecimal price, String size, boolean available, String imageUrl, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.size = size;
        this.available = available;
        this.imageUrl = imageUrl;
        this.category = category;
    }
}
