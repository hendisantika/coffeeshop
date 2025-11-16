package com.hendisantika.coffeshop.controller;

import com.hendisantika.coffeshop.entity.Order;
import com.hendisantika.coffeshop.repository.OrderRepository;
import com.hendisantika.coffeshop.repository.ProductRepository;
import com.hendisantika.coffeshop.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/ui/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;

    @GetMapping
    public String listOrders(Model model, @RequestParam(required = false) String status) {
        List<Order> orders;
        if (status != null && !status.isEmpty()) {
            orders = orderRepository.findByStatus(Order.OrderStatus.valueOf(status));
            model.addAttribute("selectedStatus", status);
        } else {
            orders = orderRepository.findAllByOrderByOrderDateDesc();
        }
        model.addAttribute("orders", orders);
        model.addAttribute("statuses", Order.OrderStatus.values());
        return "orders/list";
    }

    @GetMapping("/new")
    public String newOrderForm(Model model) {
        model.addAttribute("stores", storeRepository.findAll());
        model.addAttribute("products", productRepository.findByAvailableTrue());
        return "orders/form";
    }

    @PostMapping("/save")
    public String saveOrder(@RequestParam String customerName,
                            @RequestParam String customerEmail,
                            @RequestParam String storeId,
                            @RequestParam List<String> productIds,
                            @RequestParam List<Integer> quantities,
                            RedirectAttributes redirectAttributes) {

        storeRepository.findById(UUID.fromString(storeId)).ifPresent(store -> {
            Order order = new Order(customerName, customerEmail, store);

            for (int i = 0; i < productIds.size(); i++) {
                String productId = productIds.get(i);
                int quantity = quantities.get(i);

                if (quantity > 0) {
                    productRepository.findById(productId).ifPresent(product -> {
                        Order.OrderItem item = new Order.OrderItem(
                                product.getId(),
                                product.getName(),
                                quantity,
                                product.getPrice()
                        );
                        order.addItem(item);
                    });
                }
            }

            orderRepository.save(order);
            redirectAttributes.addFlashAttribute("message", "Order created successfully! Order ID: " + order.getId());
        });

        return "redirect:/ui/orders";
    }

    @GetMapping("/{id}")
    public String viewOrder(@PathVariable String id, Model model) {
        orderRepository.findById(id).ifPresent(order -> model.addAttribute("order", order));
        return "orders/view";
    }

    @GetMapping("/{id}/update-status")
    public String updateOrderStatus(@PathVariable String id,
                                    @RequestParam String status,
                                    RedirectAttributes redirectAttributes) {
        orderRepository.findById(id).ifPresent(order -> {
            order.setStatus(Order.OrderStatus.valueOf(status));
            orderRepository.save(order);
        });
        redirectAttributes.addFlashAttribute("message", "Order status updated!");
        return "redirect:/ui/orders/" + id;
    }

    @GetMapping("/{id}/delete")
    public String deleteOrder(@PathVariable String id, RedirectAttributes redirectAttributes) {
        orderRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Order deleted successfully!");
        return "redirect:/ui/orders";
    }
}
