package com.medishare.controller;

import com.medishare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String loginPage() {
        return "login";  // login.html を表示
    }

    @PostMapping
    public String loginUser(
            @RequestParam("username") String email,
            @RequestParam("password") String password
    ) {
        boolean isAuthenticated = userService.authenticateUser(email, password);

        if (isAuthenticated) {
            System.out.println("ログイン成功: " + email);
            return "redirect:/home";
        } else {
            System.out.println("ログイン失敗: " + email);
            return "redirect:/login?error=invalid";
        }
    }
}

