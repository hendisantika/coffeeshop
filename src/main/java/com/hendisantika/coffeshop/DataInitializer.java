package com.hendisantika.coffeshop;

import com.hendisantika.coffeshop.entity.Category;
import com.hendisantika.coffeshop.entity.Product;
import com.hendisantika.coffeshop.repository.CategoryRepository;
import com.hendisantika.coffeshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Initializes sample categories and products data
 */
@Slf4j
@Component
@Order(2)
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        if (categoryRepository.count() == 0) {
            initializeCategories();
        }

        if (productRepository.count() == 0) {
            initializeProducts();
        }
    }

    private void initializeCategories() {
        log.info("Initializing categories...");

        Category hotDrinks = new Category("Hot Drinks", "Freshly brewed hot beverages", "☕");
        Category coldDrinks = new Category("Cold Drinks", "Refreshing cold beverages", "🧊");
        Category pastries = new Category("Pastries", "Fresh baked goods", "🥐");
        Category sandwiches = new Category("Sandwiches", "Delicious sandwiches and wraps", "🥪");
        Category merchandise = new Category("Merchandise", "Coffee shop branded items", "🛍️");

        categoryRepository.save(hotDrinks);
        categoryRepository.save(coldDrinks);
        categoryRepository.save(pastries);
        categoryRepository.save(sandwiches);
        categoryRepository.save(merchandise);

        log.info("Successfully initialized {} categories", categoryRepository.count());
    }

    private void initializeProducts() {
        log.info("Initializing products...");

        Category hotDrinks = categoryRepository.findByName("Hot Drinks").orElse(null);
        Category coldDrinks = categoryRepository.findByName("Cold Drinks").orElse(null);
        Category pastries = categoryRepository.findByName("Pastries").orElse(null);
        Category sandwiches = categoryRepository.findByName("Sandwiches").orElse(null);

        if (hotDrinks != null) {
            productRepository.save(new Product("Espresso", "Rich and bold single shot espresso",
                    new BigDecimal("2.95"), "Small", true, null, hotDrinks));
            productRepository.save(new Product("Cappuccino", "Espresso with steamed milk and foam",
                    new BigDecimal("4.50"), "Medium", true, null, hotDrinks));
            productRepository.save(new Product("Latte", "Smooth espresso with steamed milk",
                    new BigDecimal("4.95"), "Large", true, null, hotDrinks));
            productRepository.save(new Product("Americano", "Espresso with hot water",
                    new BigDecimal("3.50"), "Medium", true, null, hotDrinks));
            productRepository.save(new Product("Mocha", "Chocolate espresso with steamed milk",
                    new BigDecimal("5.25"), "Large", true, null, hotDrinks));
            productRepository.save(new Product("Hot Chocolate", "Rich and creamy hot chocolate",
                    new BigDecimal("3.95"), "Medium", true, null, hotDrinks));
        }

        if (coldDrinks != null) {
            productRepository.save(new Product("Iced Latte", "Chilled espresso with cold milk",
                    new BigDecimal("5.25"), "Large", true, null, coldDrinks));
            productRepository.save(new Product("Cold Brew", "Smooth cold-steeped coffee",
                    new BigDecimal("4.75"), "Large", true, null, coldDrinks));
            productRepository.save(new Product("Iced Mocha", "Chocolate espresso over ice",
                    new BigDecimal("5.50"), "Large", true, null, coldDrinks));
            productRepository.save(new Product("Frappe", "Blended coffee with ice and cream",
                    new BigDecimal("5.95"), "Large", true, null, coldDrinks));
            productRepository.save(new Product("Iced Tea", "Refreshing brewed iced tea",
                    new BigDecimal("3.25"), "Large", true, null, coldDrinks));
        }

        if (pastries != null) {
            productRepository.save(new Product("Croissant", "Buttery French pastry",
                    new BigDecimal("3.50"), "Medium", true, null, pastries));
            productRepository.save(new Product("Blueberry Muffin", "Fresh baked muffin with blueberries",
                    new BigDecimal("3.95"), "Medium", true, null, pastries));
            productRepository.save(new Product("Chocolate Chip Cookie", "Soft and chewy cookie",
                    new BigDecimal("2.50"), "Small", true, null, pastries));
            productRepository.save(new Product("Cinnamon Roll", "Sweet pastry with cinnamon glaze",
                    new BigDecimal("4.25"), "Large", true, null, pastries));
            productRepository.save(new Product("Banana Bread", "Moist banana bread slice",
                    new BigDecimal("3.75"), "Medium", true, null, pastries));
        }

        if (sandwiches != null) {
            productRepository.save(new Product("Turkey Club", "Turkey, bacon, lettuce, tomato",
                    new BigDecimal("8.95"), "Large", true, null, sandwiches));
            productRepository.save(new Product("Veggie Wrap", "Fresh vegetables in a spinach wrap",
                    new BigDecimal("7.50"), "Medium", true, null, sandwiches));
            productRepository.save(new Product("Ham & Cheese", "Classic ham and cheese sandwich",
                    new BigDecimal("7.95"), "Medium", true, null, sandwiches));
            productRepository.save(new Product("Chicken Panini", "Grilled chicken with pesto",
                    new BigDecimal("9.50"), "Large", true, null, sandwiches));
        }

        log.info("Successfully initialized {} products", productRepository.count());
    }
}
