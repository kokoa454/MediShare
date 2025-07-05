package com.medishare.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.medishare.model.USER_MEDICINE;
import com.medishare.model.USER_TIMETABLE;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserService userService;
    private final MedicineService medicineService;
    private final LineService lineService;

    @Scheduled(fixedDelay = 60000) 
    public void checkAndSendWarningBeforeMedication() {
        String now = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        List<Integer> userIds = userService.getAllUserIds();

        for (Integer userId : userIds) {
            String userLineId = userService.getUserLineIdByUserId(userId);
            USER_TIMETABLE userTimetable = userService.getUserTimetableByUserId(userId);
            List<USER_MEDICINE> medicines = medicineService.getAllMedicinesByUserId(userId);
            java.util.Map<String, List<String>> medicinesForNotificationMap = new java.util.HashMap<>();

            for (USER_MEDICINE medicine : medicines) {
                if (medicine.isCompleted()) {
                    continue;
                }

                String method = medicine.getMedicationMethod();
                boolean needToNotify = switch (method) {
                    case "起床時" -> now.equals(userTimetable.getWakeUp());
                    case "朝食前" -> now.equals(userTimetable.getBeforeBreakfast());
                    case "朝食後" -> now.equals(userTimetable.getAfterBreakfast());
                    case "昼食前" -> now.equals(userTimetable.getBeforeLunch());
                    case "昼食後" -> now.equals(userTimetable.getAfterLunch());
                    case "夕食前" -> now.equals(userTimetable.getBeforeDinner());
                    case "夕食後" -> now.equals(userTimetable.getAfterDinner());
                    case "就寝前" -> now.equals(userTimetable.getBeforeSleep());
                    case "食間" -> now.equals(userTimetable.getBetweenMeals());
                    default -> method.matches("\\d{1,2}時") && now.equals(
                            String.format("%02d:00", Integer.valueOf(method.replace("時", "")))
                    );
                };

                if (needToNotify) {
                    medicinesForNotificationMap
                            .computeIfAbsent(method, k -> new java.util.ArrayList<>())
                            .add(medicine.getMedicineUserInput());
                }
            }

            for (var entry : medicinesForNotificationMap.entrySet()) {
                String timing = entry.getKey();
                List<String> medicinesForNotification = entry.getValue();

                StringBuilder message = new StringBuilder();
                message.append("【MediShare】\n");
                message.append(timing).append("の服薬時間となりました。\n");
                message.append("以下の薬を服用してください：\n");
                for (String med : medicinesForNotification) {
                    message.append("- ").append(med).append("\n");
                }

                lineService.notifyBeforeMedicationLine(userLineId, message.toString());
            }
        }
    }
}
