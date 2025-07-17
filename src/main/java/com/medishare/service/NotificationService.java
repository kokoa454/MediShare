package com.medishare.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medishare.model.TimeZoneCategory;
import com.medishare.model.USER_MEDICINE;
import com.medishare.model.USER_TIMETABLE;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserService userService;
    private final UserMedicineService userMedicineService;
    private final LineService lineService;
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Scheduled(fixedDelay = 60000) // 1分ごとに実行
    public void checkAndSendNotificationBeforeMedication() {
        logger.info("Start scanning user notifications before medication");
        String now = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        List<Integer> userIds = userService.getAllUserIds();

        for (Integer userId : userIds) {
            String userLineId = userService.getUserLineId(userId);

            if(userLineId != null) {
                USER_TIMETABLE userTimetable = userService.getUserTimetableByUserId(userId);
                List<USER_MEDICINE> medicines = userMedicineService.getAllMedicinesByUserId(userId);
                java.util.Map<String, List<String>> medicinesForNotificationMap = new java.util.HashMap<>();

                for (USER_MEDICINE medicine : medicines) {
                    if (medicine.isCompleted()) {
                        continue;
                    }

                    String method = medicine.getMedicationMethod();

                    boolean needToNotify = switch (TimeZoneCategory.fromLabel(method)) {
                        case WAKE_UP -> now.equals(userTimetable.getWakeUp());
                        case BEFORE_BREAKFAST -> now.equals(userTimetable.getBeforeBreakfast());
                        case AFTER_BREAKFAST -> now.equals(userTimetable.getAfterBreakfast());
                        case BEFORE_LUNCH -> now.equals(userTimetable.getBeforeLunch());
                        case AFTER_LUNCH -> now.equals(userTimetable.getAfterLunch());
                        case BEFORE_DINNER -> now.equals(userTimetable.getBeforeDinner());
                        case AFTER_DINNER -> now.equals(userTimetable.getAfterDinner());
                        case BEFORE_SLEEP -> now.equals(userTimetable.getBeforeSleep());
                        case BETWEEN_MEALS -> now.equals(userTimetable.getBetweenMeals());
                        // 時間指定のケース
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

                    logger.info("Sending pre-medication notification: user ID={}, user LINE ID={}, message={}", userId, userLineId, message.toString());
                    lineService.notifyUserBeforeMedicationLine(userLineId, message.toString());
                }
            } else {
                logger.warn("Failed to send pre-medication notification (user LINE ID not set): user ID={}", userId);
            }
        }
        logger.info("Finished scanning user notifications before medication");
    }

    @Scheduled(fixedDelay = 300000) // 5分ごとに実行
    public void checkAndSendWarningUncompletedMedication() {
        logger.info("Start scanning user notifications for uncompleted medication");
        LocalTime checkTime = LocalTime.now();
        List<Integer> userIds = userService.getAllUserIds();

        for (Integer userId : userIds) {
            String userLineId = userService.getUserLineId(userId);
            String familyLineId = userService.getFamilyLineId(userId);

            if(userLineId != null) {
                USER_TIMETABLE userTimetable = userService.getUserTimetableByUserId(userId);
                List<USER_MEDICINE> medicines = userMedicineService.getAllMedicinesByUserId(userId);
                java.util.Map<String, List<String>> medicinesForNotificationMap = new java.util.HashMap<>();

                for (USER_MEDICINE medicine : medicines) {
                    if (medicine.isCompleted()) {
                        continue;
                    }

                    String method = medicine.getMedicationMethod();
                    boolean needToNotify = switch (TimeZoneCategory.fromLabel(method)) {
                        case WAKE_UP -> checkTime.isAfter(LocalTime.parse(userTimetable.getWakeUp()).plusMinutes(30));
                        case BEFORE_BREAKFAST -> checkTime.isAfter(LocalTime.parse(userTimetable.getBeforeBreakfast()).plusMinutes(30));
                        case AFTER_BREAKFAST -> checkTime.isAfter(LocalTime.parse(userTimetable.getAfterBreakfast()).plusMinutes(30));
                        case BEFORE_LUNCH -> checkTime.isAfter(LocalTime.parse(userTimetable.getBeforeLunch()).plusMinutes(30));
                        case AFTER_LUNCH -> checkTime.isAfter(LocalTime.parse(userTimetable.getAfterLunch()).plusMinutes(30));
                        case BEFORE_DINNER -> checkTime.isAfter(LocalTime.parse(userTimetable.getBeforeDinner()).plusMinutes(30));
                        case AFTER_DINNER -> checkTime.isAfter(LocalTime.parse(userTimetable.getAfterDinner()).plusMinutes(30));
                        case BEFORE_SLEEP -> checkTime.isAfter(LocalTime.parse(userTimetable.getBeforeSleep()).plusMinutes(30));
                        case BETWEEN_MEALS -> checkTime.isAfter(LocalTime.parse(userTimetable.getBetweenMeals()).plusMinutes(30));
                        // 時間指定のケース
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
                    logger.info("Sending notification for unfinished medication: user ID={}, user LINE ID={}, message={}", userId, userLineId, message.toString());
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
                        logger.info("Sending notification for unfinished medication: user ID={}, family LINE ID={}, message={}", userId, familyLineId, message.toString());
                    }
                } else {
                    logger.warn("Failed to send notification for unfinished medication (family LINE ID not set): user ID={}", userId);
                }
            } else {
                logger.warn("Failed to send notification for unfinished medication (user LINE ID not set): user ID={}", userId);
            }
        }
        logger.info("Finished scanning notifications for unfinished medication");
    }
}
