package com.medishare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/terms_of_use")
public class TermsOfUseController {
    @GetMapping
    public String termsOfUse_page() {
        return "terms_of_use";  // terms_of_use.html を表示
    }
}