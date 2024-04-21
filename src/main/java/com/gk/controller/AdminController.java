package com.gk.controller;

import com.gk.model.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class AdminController {

    private List<Category> categories = new ArrayList<>();
    private AtomicInteger id = new AtomicInteger(1);

    @GetMapping("/adminHome")
    public String adminHome() {
        return "adminHome";
    }

    @GetMapping("/admin/categories")
    public String getCategory(Model model) {
        List<Category> category = List.of(new Category(1l, "Fast Food", "Fast Food"));
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String getProducts(Model model) {
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String addCategory(@ModelAttribute("category") Category category) {
        //save data
        categories.add(category);
        System.out.println(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategories(@PathVariable Long id) {
        categories.stream().filter(c -> Objects.equals(c.getId(), id)).findFirst()
                .ifPresent(category -> categories.remove(category));
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCategories(@PathVariable Integer id) {
        return "redirect:/admin/categories/add";
    }

}
