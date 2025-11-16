package com.hendisantika.coffeshop.controller;

import com.hendisantika.coffeshop.entity.Store;
import com.hendisantika.coffeshop.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/ui/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreRepository storeRepository;

    @GetMapping
    public String listStores(Model model) {
        model.addAttribute("stores", storeRepository.findAll());
        return "stores/list";
    }

    @GetMapping("/new")
    public String newStoreForm(Model model) {
        model.addAttribute("store", new StoreForm());
        return "stores/form";
    }

    @PostMapping("/save")
    public String saveStore(StoreForm form, RedirectAttributes redirectAttributes) {
        Point location = new Point(form.getLongitude(), form.getLatitude());
        Store.Address address = new Store.Address(form.getStreet(), form.getCity(), form.getZip(), location);
        Store store = new Store(form.getName(), address);

        storeRepository.save(store);
        redirectAttributes.addFlashAttribute("message", "Store saved successfully!");
        return "redirect:/ui/stores";
    }

    @GetMapping("/{id}")
    public String viewStore(@PathVariable UUID id, Model model) {
        storeRepository.findById(id).ifPresent(store -> model.addAttribute("store", store));
        return "stores/view";
    }

    @GetMapping("/{id}/delete")
    public String deleteStore(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        storeRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Store deleted successfully!");
        return "redirect:/ui/stores";
    }

    // Form backing object
    public static class StoreForm {
        private String name;
        private String street;
        private String city;
        private String zip;
        private double longitude;
        private double latitude;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
    }
}
