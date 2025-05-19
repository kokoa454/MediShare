package com.medishare.controller;

import com.medishare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registerPage() {
        return "register";  // register.html を表示
    }

    @PostMapping
    public String registerUser(
            @RequestParam("username") String email,
            @RequestParam("password") String password
    ) {
        boolean isRegistered = userService.registerUser(email, password);

        if (!isRegistered) {
            System.out.println("すでに登録されているユーザーです");
            return "redirect:/register?error=exists";
        }

        System.out.println("ユーザー登録成功: " + email);
        return "redirect:/login";
    }
}

