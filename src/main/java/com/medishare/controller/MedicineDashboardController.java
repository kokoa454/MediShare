package com.medishare.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.medishare.model.USER_MEDICINE;
import com.medishare.service.UserMedicineService;
import com.medishare.service.UserService;
import com.medishare.model.TimeZoneCategory;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/medicine_dashboard")
@RequiredArgsConstructor
public class MedicineDashboardController {
    private final UserMedicineService userMedicineService;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(MedicineDashboardController.class);

    @GetMapping
    public String medicineDashboardPage(@RequestParam(name = "method", required = true) String method, @RequestParam(name = "search", required = false) String searchKeyword ,Model model) {
        // ログインユーザーのIDを取得
        int userId = userService.getLoginUserId();
        String titleName = "";

        for (TimeZoneCategory category : TimeZoneCategory.values()) {
            if (category.getAttrPrefix().equals(method)) {
                titleName = category.getLabel();
                break;
            }
        }
        
        model.addAttribute("titleName", titleName);

        model.addAttribute("method", method);

        List<USER_MEDICINE> medicines;

        if(searchKeyword != null) {
            medicines = userMedicineService.getMedicineListByUserAndMedicationMethodAndSearch(userId, titleName, searchKeyword);
        } else {
            medicines = userMedicineService.getMedicineListByUserAndMedicationMethod(userId, titleName);
        }
        model.addAttribute("medicines", medicines);        

        return "medicine_dashboard";
    }

    @PostMapping("/delete_medicine")
    public ResponseEntity<String> deleteMedicine(@RequestBody List<Integer> userMedicineIds) {
        try {
            userMedicineService.deleteMedicine(userMedicineIds);
            logger.info("Successfully deleted medicine: user ID={}, user medicine ID={}", userService.getLoginUserId(), userMedicineIds);
            return ResponseEntity.ok("お薬を削除しました");
        } catch (Exception e) {
            logger.error("Failed to delete medicine: user ID={}, user medicine ID={}, error message={}", userService.getLoginUserId(), userMedicineIds, e.getMessage());
            return ResponseEntity.status(500).body("お薬の削除に失敗しました");
        }
    }
}
