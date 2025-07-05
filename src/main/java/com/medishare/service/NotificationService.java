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

    @Scheduled(fixedDelay = 60000) // 1分ごとに実行
    public void checkAndSendNotificationBeforeMedication() {
        String now = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        List<Integer> userIds = userService.getAllUserIds();

        for (Integer userId : userIds) {
            String userLineId = userService.getUserLineId(userId);

            if(userLineId != null) {
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

                    lineService.notifyUserBeforeMedicationLine(userLineId, message.toString());
                }
            }
        }
    }

    @Scheduled(fixedDelay = 300000) // 5分ごとに実行
    public void checkAndSendWarningUncompletedMedication() {
        LocalTime checkTime = LocalTime.now();
        List<Integer> userIds = userService.getAllUserIds();

        for (Integer userId : userIds) {
            String userLineId = userService.getUserLineId(userId);
            String familyLineId = userService.getFamilyLineId(userId);

            if(userLineId != null) {
                USER_TIMETABLE userTimetable = userService.getUserTimetableByUserId(userId);
                List<USER_MEDICINE> medicines = medicineService.getAllMedicinesByUserId(userId);
                java.util.Map<String, List<String>> medicinesForNotificationMap = new java.util.HashMap<>();

                for (USER_MEDICINE medicine : medicines) {
                    if (medicine.isCompleted()) {
                        continue;
                    }

                    String method = medicine.getMedicationMethod();
                    boolean needToNotify = switch (method) {
                         case "起床時" -> checkTime.isAfter(LocalTime.parse(userTimetable.getWakeUp()).plusMinutes(30));
                        case "朝食前" -> checkTime.isAfter(LocalTime.parse(userTimetable.getBeforeBreakfast()).plusMinutes(30));
                        case "朝食後" -> checkTime.isAfter(LocalTime.parse(userTimetable.getAfterBreakfast()).plusMinutes(30));
                        case "昼食前" -> checkTime.isAfter(LocalTime.parse(userTimetable.getBeforeLunch()).plusMinutes(30));
                        case "昼食後" -> checkTime.isAfter(LocalTime.parse(userTimetable.getAfterLunch()).plusMinutes(30));
                        case "夕食前" -> checkTime.isAfter(LocalTime.parse(userTimetable.getBeforeDinner()).plusMinutes(30));
                        case "夕食後" -> checkTime.isAfter(LocalTime.parse(userTimetable.getAfterDinner()).plusMinutes(30));
                        case "就寝前" -> checkTime.isAfter(LocalTime.parse(userTimetable.getBeforeSleep()).plusMinutes(30));
                        case "食間" -> checkTime.isAfter(LocalTime.parse(userTimetable.getBetweenMeals()).plusMinutes(30));
                        default -> method.matches("\\d{1,2}時") && checkTime.isAfter(
                                LocalTime.parse(String.format("%02d:00", Integer.valueOf(method.replace("時", "")))).plusMinutes(30)
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
                    message.append(timing).append("の服薬時間から30分以上経過しています。\n");
                    message.append("以下の薬を服用してください：\n");
                    for (int i = 0; i < medicinesForNotification.size(); i++) {
                        if(i < medicinesForNotification.size() - 1) {
                            message.append("- ").append(medicinesForNotification.get(i)).append("\n");
                        } else {
                            message.append("- ").append(medicinesForNotification.get(i));
                        }
                    }

                    lineService.notifyUserBeforeMedicationLine(userLineId, message.toString());
                }

                if(familyLineId != null) {
                    for (var entry : medicinesForNotificationMap.entrySet()) {
                        String timing = entry.getKey();
                        List<String> medicinesForNotification = entry.getValue();

                        StringBuilder message = new StringBuilder();
                        message.append("【MediShare】\n");
                        message.append(timing).append("の服薬時間から30分以上経過しています。\n");
                        message.append("以下の薬を服用するよう、ご連絡することをおすすめします：\n");
                        for (int i = 0; i < medicinesForNotification.size(); i++) {
                            if(i < medicinesForNotification.size() - 1) {
                                message.append("- ").append(medicinesForNotification.get(i)).append("\n");
                            } else {
                                message.append("- ").append(medicinesForNotification.get(i));
                            }
                        }

                        lineService.notifyFamilyBeforeMedicationLine(familyLineId, message.toString());
                }
                }
            }
        }
    }
}
