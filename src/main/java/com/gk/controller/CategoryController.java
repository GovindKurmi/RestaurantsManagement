package com.gk.controller;

import com.gk.model.Category;
import com.gk.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/fetchAll")
    public String getAllCategory(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        return "categories";
    }

    @GetMapping("/addPage")
    public String addCategoriesPage(Model model) {
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping("/addNew")
    public String addCategory(@ModelAttribute("category") Category category) {
        categoryService.addCategories(category);
        System.out.println(category);
        return "redirect:/category/fetchAll";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategories(@PathVariable Long id) {
        categoryService.deleteById(id);
        return "redirect:/category/fetchAll";
    }

    @GetMapping("/update/{id}")
    public String updateCategories(@PathVariable Integer id) {
        categoryService.updateCategories(id);
        return "redirect:/category/addPage";
    }

}
