package com.medishare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/edit_medicine")
public class EditMedicineController {
    @GetMapping
    public String edit_medicine() {
        return "edit_medicine";  // login.html を表示
    }
}
