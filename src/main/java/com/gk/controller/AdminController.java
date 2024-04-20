package com.gk.controller;

import com.gk.model.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    private List<Category> categories = new ArrayList<>();

    @GetMapping("/adminHome")
    public String adminHome() {
        return "adminHome";
    }

    @GetMapping("/admin/categories")
    public String getCategory(Model model) {
        List<Category> category = List.of(new Category(1, "Fast Food", "Fast Food"));
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String getProducts(Model model) {
        Category category = new Category(1, "Fast Food", "Fast Food");
        model.addAttribute("category", category);
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
    public String deleteCategories(@PathVariable Integer id) {
        categories.stream().filter(c -> c.getId() == id).findFirst()
                .ifPresent(category -> categories.remove(category));
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCategories(@PathVariable Integer id) {
        return "redirect:/admin/categories/add";
    }

}
