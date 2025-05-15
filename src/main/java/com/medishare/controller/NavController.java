package com.medishare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavController {
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register_account")
    public String register_account(){ return "register_account"; }
}
