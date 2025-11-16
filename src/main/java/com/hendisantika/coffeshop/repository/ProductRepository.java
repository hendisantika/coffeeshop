package com.hendisantika.coffeshop.repository;

import com.hendisantika.coffeshop.entity.Category;
import com.hendisantika.coffeshop.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByCategory(Category category);

    List<Product> findByCategoryId(String categoryId);

    List<Product> findByAvailableTrue();

    List<Product> findByNameContainingIgnoreCase(String name);
}
