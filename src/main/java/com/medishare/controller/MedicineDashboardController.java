package com.medishare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/medicine_dashboard")
public class MedicineDashboardController {
    @GetMapping
    public String medicineDashboardPage(@RequestParam(name = "method", required = true) String method ,Model model) {
        switch(method){
            case "wakeUp":
                model.addAttribute("titleName", "起床時");
                break;
            case "beforeBreakfast":
                model.addAttribute("titleName", "朝食前");
                break;
            case "afterBreakfast":
                model.addAttribute("titleName", "朝食後");
                break;
            case "beforeLunch":
                model.addAttribute("titleName", "昼食前");
                break;
            case "afterLunch":
                model.addAttribute("titleName", "昼食後");
                break;
            case "beforeDinner":
                model.addAttribute("titleName", "夕食前");
                break;
            case "afterDinner":
                model.addAttribute("titleName", "夕食後");
                break;
            case "beforeSleep":
                model.addAttribute("titleName", "就寝前");
                break;
            case "betweenMeal":
                model.addAttribute("titleName", "食間");
                break;

            case "zeroOClock":
                model.addAttribute("titleName", "0時");
                break;
            case "oneOClock":
                model.addAttribute("titleName", "1時");
                break;
            case "twoOClock":
                model.addAttribute("titleName", "2時");
                break;
            case "threeOClock":
                model.addAttribute("titleName", "3時");
                break;
            case "fourOClock":
                model.addAttribute("titleName", "4時");
                break;
            case "fiveOClock":
                model.addAttribute("titleName", "5時");
                break;
            case "sixOClock":
                model.addAttribute("titleName", "6時");
                break;
            case "sevenOClock":
                model.addAttribute("titleName", "7時");
                break;
            case "eightOClock":
                model.addAttribute("titleName", "8時");
                break;
            case "nineOClock":
                model.addAttribute("titleName", "9時");
                break;
            case "tenOClock":
                model.addAttribute("titleName", "10時");
                break;
            case "elevenOClock":
                model.addAttribute("titleName", "11時");
                break;
            case "twelveOClock":
                model.addAttribute("titleName", "12時");
                break;
            case "thirteenOClock":
                model.addAttribute("titleName", "13時");
                break;
            case "fourteenOClock":
                model.addAttribute("titleName", "14時");
                break;
            case "fifteenOClock":
                model.addAttribute("titleName", "15時");
                break;
            case "sixteenOClock":
                model.addAttribute("titleName", "16時");
                break;
            case "seventeenOClock":
                model.addAttribute("titleName", "17時");
                break;
            case "eighteenOClock":
                model.addAttribute("titleName", "18時");
                break;
            case "nineteenOClock":
                model.addAttribute("titleName", "19時");
                break;
            case "twentyOClock":
                model.addAttribute("titleName", "20時");
                break;
            case "twentyOneOClock":
                model.addAttribute("titleName", "21時");
                break;
            case "twentyTwoOClock":
                model.addAttribute("titleName", "22時");
                break;
            case "twentyThreeOClock":
                model.addAttribute("titleName", "23時");
                break;
            }
        
        return "medicine_dashboard";
    }
}
