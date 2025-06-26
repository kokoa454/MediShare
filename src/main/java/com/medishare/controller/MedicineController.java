package com.medishare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.medishare.model.USER_DATABASE;
import com.medishare.model.USER_MEDICINE;
import com.medishare.repository.UserMedicineRepository;
import com.medishare.repository.UserRepository;

@Controller
public class MedicineController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMedicineRepository userMedicineRepository;

    @PostMapping("/register_medicine")
    public String registerMedicine(
            @RequestParam("medicineUserInput") String medicineUserInput,
            @RequestParam("medicineOfficialName") String medicineOfficialName,
            @RequestParam("prescriptionDays") String prescriptionDays,
            @RequestParam("userComment") String userComment,
            @RequestParam("timingCode") String timingCode
    ) {
        // ユーザー取得
        Authentication userData = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = userData.getName(); // ログインユーザーのメールアドレスを取得
        System.out.println("ユーザメアド" + userEmail);
        USER_DATABASE user = userRepository.findByUserEmail(userEmail); // メールアドレスでユーザーを検索
        System.out.println("ユーザー情報" + user);

        // タイミングコード → 日本語ラベルに変換
        String medicationMethod = convertTimingCodeToLabel(timingCode);

        // エンティティに詰めて保存
        USER_MEDICINE medicine = new USER_MEDICINE(
                user,
                medicineUserInput,
                medicineOfficialName,
                prescriptionDays,
                medicationMethod,
                userComment
        );

        userMedicineRepository.save(medicine);

        return "redirect:/dashboard"; // 遷移先指定
    }

    // 🔁 タイミングコード変換処理
    private String convertTimingCodeToLabel(String code) {
        return switch (code) {
            case "000" -> "起床時";
            case "001" -> "朝食前";
            case "002" -> "朝食後";
            case "003" -> "昼食前";
            case "004" -> "昼食後";
            case "005" -> "夕食前";
            case "006" -> "夕食後";
            case "007" -> "就寝前";
            case "008" -> "食間";
            default -> (Integer.parseInt(code) - 100) + "時"; // 時間指定処理
        };
    }
}
