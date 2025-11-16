package com.hendisantika.coffeshop.controller;

import com.hendisantika.coffeshop.entity.Product;
import com.hendisantika.coffeshop.repository.CategoryRepository;
import com.hendisantika.coffeshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ui/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public String listProducts(Model model, @RequestParam(required = false) String categoryId) {
        if (categoryId != null && !categoryId.isEmpty()) {
            model.addAttribute("products", productRepository.findByCategoryId(categoryId));
            model.addAttribute("selectedCategoryId", categoryId);
        } else {
            model.addAttribute("products", productRepository.findAll());
        }
        model.addAttribute("categories", categoryRepository.findAll());
        return "products/list";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "products/form";
    }

    @GetMapping("/{id}/edit")
    public String editProductForm(@PathVariable String id, Model model) {
        productRepository.findById(id).ifPresent(product -> model.addAttribute("product", product));
        model.addAttribute("categories", categoryRepository.findAll());
        return "products/form";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product,
                              @RequestParam String categoryId,
                              RedirectAttributes redirectAttributes) {
        categoryRepository.findById(categoryId).ifPresent(product::setCategory);
        productRepository.save(product);
        redirectAttributes.addFlashAttribute("message", "Product saved successfully!");
        return "redirect:/ui/products";
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable String id, Model model) {
        productRepository.findById(id).ifPresent(product -> model.addAttribute("product", product));
        return "products/view";
    }

    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable String id, RedirectAttributes redirectAttributes) {
        productRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Product deleted successfully!");
        return "redirect:/ui/products";
    }

    @GetMapping("/{id}/toggle-availability")
    public String toggleAvailability(@PathVariable String id, RedirectAttributes redirectAttributes) {
        productRepository.findById(id).ifPresent(product -> {
            product.setAvailable(!product.isAvailable());
            productRepository.save(product);
        });
        redirectAttributes.addFlashAttribute("message", "Product availability updated!");
        return "redirect:/ui/products";
    }
}
