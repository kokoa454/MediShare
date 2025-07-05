package com.medishare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about_app")
public class AboutAppController {
    @GetMapping
    public String aboutApp() {
        return "about_app";  // about_app.html を表示
    }
}