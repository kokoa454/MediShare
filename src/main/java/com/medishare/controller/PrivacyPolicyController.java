package com.medishare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/privacy_policy")
public class PrivacyPolicyController {
    @GetMapping
    public String privacyPolicy_Page() {
        return "privacy_policy";  // privacy_policy.html を表示
    }
}