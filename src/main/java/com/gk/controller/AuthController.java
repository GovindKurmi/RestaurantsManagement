package com.gk.controller;

import com.gk.common.GlobalData;
import com.gk.model.User;
import com.gk.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

private final AuthService authService;

    @GetMapping("/login")
    public String login() {
        GlobalData.cart.clear();
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") User user) {
        authService.registration(user);
        return "login";
    }
}
