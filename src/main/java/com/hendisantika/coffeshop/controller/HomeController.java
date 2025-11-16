package com.hendisantika.coffeshop.controller;

import com.hendisantika.coffeshop.repository.CategoryRepository;
import com.hendisantika.coffeshop.repository.OrderRepository;
import com.hendisantika.coffeshop.repository.ProductRepository;
import com.hendisantika.coffeshop.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("storeCount", storeRepository.count());
        model.addAttribute("productCount", productRepository.count());
        model.addAttribute("categoryCount", categoryRepository.count());
        model.addAttribute("orderCount", orderRepository.count());
        model.addAttribute("recentOrders", orderRepository.findAllByOrderByOrderDateDesc()
                .stream().limit(5).toList());
        return "home";
    }
}
