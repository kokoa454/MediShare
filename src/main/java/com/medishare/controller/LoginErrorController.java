package com.medishare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login_error")
public class LoginErrorController {
    @GetMapping
    public String login_errorPage() {
        return "login_error";  // login_error.html を表示
    }
}
