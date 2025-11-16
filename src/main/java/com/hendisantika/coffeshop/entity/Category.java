package com.hendisantika.coffeshop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Category entity for organizing products
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document
public class Category {
    @Id
    private String id;
    private String name;
    private String description;
    private String icon;

    public Category(String name, String description, String icon) {
        this.name = name;
        this.description = description;
        this.icon = icon;
    }
}
