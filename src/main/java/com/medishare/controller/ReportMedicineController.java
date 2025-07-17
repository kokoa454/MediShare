package com.medishare.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medishare.dto.ReportDTO;
import com.medishare.repository.UserRepository;
import com.medishare.service.LineService;
import com.medishare.service.MailService;
import com.medishare.service.UserMedicineService;
import com.medishare.service.UserService;
import com.medishare.model.TimeZoneCategory;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/report_medicine")
@RequiredArgsConstructor
public class ReportMedicineController {
    private final UserMedicineService userMedicineService;
    private final UserService userService;
    private final MailService mailService;
    private final LineService lineService;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(ReportMedicineController.class);

    @GetMapping
    public String report_medicinePage(@RequestParam(name = "method", required = true) String method, @RequestParam(name = "userMedicineIds", required = true) String userMedicineIds, Model model) {
        String medicationMethod = "";

        for (TimeZoneCategory category : TimeZoneCategory.values()) {
            if (category.getAttrPrefix().equals(method)) {
                medicationMethod = category.getLabel();
                break;
            }
        }

        model.addAttribute("medicationMethod", medicationMethod);

        List<String> medicines = new ArrayList<>();
        for(String userMedicineId : userMedicineIds.split(",")) {
            medicines.add(userMedicineService.getMedicineUserInputByUserMedicineId(Integer.parseInt(userMedicineId)).split(",")[0]);
        }

        model.addAttribute("medicines", medicines);

        int userId = userService.getLoginUserId();
        String familyEmail = userService.getFamilyEmail(userId);
        
        model.addAttribute("familyEmail", familyEmail);
        
        String familyLineId = userService.getFamilyLineId(userId);
        model.addAttribute("familyLineId", familyLineId);

        return "report_medicine";
    }

    @PostMapping("/send_mail")
    @SuppressWarnings("UseSpecificCatch")
    public ResponseEntity<String> report_medicineWithMail(@RequestBody ReportDTO reportData, @RequestParam(name = "userMedicineIds", required = true) String userMedicineIds) {
        String userEmail = reportData.getUserEmail();
        String familyEmail = reportData.getFamilyEmail();
        List<String> medicineNames = reportData.getMedicines();
        String medicationMethod = reportData.getMedicationMethod();
        String userCondition = reportData.getUserCondition();
        String userComment = reportData.getUserComment();
        String completedDate = reportData.getCompletedDate();
        String userName = userRepository.findByUserEmail(userEmail).getUserName();
        System.out.println(reportData);

        try {
            mailService.sendMail(userName, userEmail ,familyEmail, medicineNames, medicationMethod, userCondition, userComment);

            for(String userMedicineId : userMedicineIds.split(",")) {
                userMedicineService.completeMedicine(Integer.parseInt(userMedicineId), completedDate);
            }

            logger.info("Email sent successfully: user ID={}, email address={}", userService.getLoginUserId(), userEmail);
            return ResponseEntity.ok("メールで報告しました");
        } catch (Exception e) {
            logger.error("Failed to send email: user ID={}, email address={}, error={}", userService.getLoginUserId(), userEmail, e.getMessage());
            return ResponseEntity.status(500).body("メールの送信に失敗しました");
        }


    }

    @PostMapping("/send_line")
    @SuppressWarnings("UseSpecificCatch")
    public ResponseEntity<String> report_medicineWithLine(@RequestBody ReportDTO reportData, @RequestParam(name = "userMedicineIds", required = true) String userMedicineIds) {
        String userEmail = reportData.getUserEmail();
        List<String> medicineNames = reportData.getMedicines();
        String medicationMethod = reportData.getMedicationMethod();
        String userCondition = reportData.getUserCondition();
        String userComment = reportData.getUserComment();
        String completedDate = reportData.getCompletedDate();
        String userName = userRepository.findByUserEmail(userEmail).getUserName();
        String familyLineId = userRepository.findByUserEmail(userEmail).getFamilyLineId();

        System.out.println(reportData);

        try {
            lineService.reportLine(userName, userEmail, familyLineId, medicineNames, medicationMethod, userCondition, userComment);

            for(String userMedicineId : userMedicineIds.split(",")) {
                userMedicineService.completeMedicine(Integer.parseInt(userMedicineId), completedDate);
            }

            logger.info("LINE message sent successfully: user ID={}, LINE ID={}", userService.getLoginUserId(), familyLineId);
            return ResponseEntity.ok("LINEで報告しました");
        } catch (Exception e) {
            logger.error("Failed to send LINE message: user ID={}, LINE ID={}, error={}", userService.getLoginUserId(), familyLineId, e.getMessage());
            return ResponseEntity.status(500).body("LINEの送信に失敗しました");
        }
    }
}
