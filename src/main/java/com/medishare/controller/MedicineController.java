package com.medishare.controller;

import com.medishare.model.USER_DATABASE;
import com.medishare.model.USER_MEDICINE;
import com.medishare.repository.MedicineRepository;
import com.medishare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MedicineController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedicineRepository userMedicineRepository;

    @PostMapping("/register_medicine")
    public String registerMedicine(
            @RequestParam("userEmail") String userEmail,
            @RequestParam("medicineUserInput") String userInput,
            @RequestParam("medicineOfficialName") String officialName,
            @RequestParam("prescriptionDays") String days,
            @RequestParam("userComment") String comment,
            @RequestParam("timingCode") String timingCode
    ) {
        // ユーザー取得
        USER_DATABASE user = userRepository.findByUserEmail(userEmail);
        if (user == null) {
            return "error";
        }

        // タイミングコード → 日本語ラベルに変換
        String method = convertTimingCodeToLabel(timingCode);

        // エンティティに詰めて保存
        USER_MEDICINE medicine = new USER_MEDICINE(
                user,
                userInput,
                officialName,
                days,
                method,
                comment
        );

        userMedicineRepository.save(medicine);

        return ""; // 遷移先指定
    }

    // 🔁 タイミングコード変換処理
    private String convertTimingCodeToLabel(String code) {
        switch (code) {
            case "000":
                return "起床時";
            case "001":
                return "朝食前";
            case "002":
                return "朝食後";
            case "003":
                return "昼食前";
            case "004":
                return "昼食後";
            case "005":
                return "夕食前";
            case "006":
                return "夕食後";
            case "007":
                return "就寝前";
            case "008":
                return "食間";
            default:
                return (Integer.parseInt(code) - 100) + "時"; // 時間指定処理

        }
    }
}
