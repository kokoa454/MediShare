package com.medishare.service;

import java.util.List;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;

@LineMessageHandler
@RequiredArgsConstructor
public class LineService {
    private final LineMessagingClient lineMessagingClient;
    private static final Logger logger = LoggerFactory.getLogger(LineService.class);

    @EventMapping
    public void handleFollowEvent(FollowEvent followEvent) {
        String userId = followEvent.getSource().getUserId();
        String replyToken = followEvent.getReplyToken();
        
        String message = "あなたのLINEのユーザーIDは以下の通りです↴";
        
        lineMessagingClient.replyMessage(new ReplyMessage(
            replyToken, 
            new TextMessage(message))
        ).whenComplete((response, exception) -> {
            if (exception != null) {
                logger.error("Failed to reply to follow event message: error message={}", exception.getMessage());
            } else {
                logger.info("Successfully replied to follow event message: user LINE ID={}", userId);
            }
        });

        lineMessagingClient.pushMessage(new PushMessage(
            userId, 
            new TextMessage(userId)
        )).whenComplete((response, exception) -> {
            if (exception != null) {
                logger.error("Failed to push user ID in follow event: error message={}", exception.getMessage());
            } else {
                logger.info("Successfully pushed user ID in follow event: user LINE ID={}", userId);
            }
        });
    }

    public void reportLine(String userName, String userEmail, String familyLineId, List<String> medicineNames, String medicationMethod, String userCondition, String userComment) {
        String message = String.format(
            """
            %s(%s)さんの服薬状況をお知らせいたします。

            ■ 服用したお薬
            %s

            ■ 飲んだタイミング・時間
            %s

            ■ 体調のご様子
            %s

            ■ コメント
            %s

            Medishareがご家族の皆さまの安心につながれば幸いです。
            本メールは自動送信です。ご不明点がございましたら、アプリ開発者(hm-c24036@sist.ac.jpかhm-c24063@sist.ac.jp)へご相談ください。
            """,
            userName, userEmail, String.join("\n", medicineNames), medicationMethod, userCondition, userComment);

        lineMessagingClient.pushMessage(new PushMessage(
            familyLineId, 
            new TextMessage(message)
        )).whenComplete((response, exception) -> {
            if (exception != null) {
                logger.error("Failed to send medication report message: family LINE ID={}, error message={}", familyLineId, exception.getMessage());
            } else {
                logger.info("Medication report message sent successfully: family LINE ID={}", familyLineId);
            }
        });
    }

    public void notifyUserBeforeMedicationLine(String userLineId, String message) {
        lineMessagingClient.pushMessage(new PushMessage(
            userLineId, 
            new TextMessage(message)
        )).whenComplete((response, exception) -> {
            if (exception != null) {
                logger.error("Failed to send pre-medication notification to user: user LINE ID={}, error message={}", userLineId, exception.getMessage());
            } else {
                logger.info("Pre-medication notification sent to user successfully: user LINE ID={}", userLineId);
            }
        });
    }

    public void notifyFamilyBeforeMedicationLine(String familyLineId, String message) {
        lineMessagingClient.pushMessage(new PushMessage(
            familyLineId, 
            new TextMessage(message)
        )).whenComplete((response, exception) -> {
            if (exception != null) {
                logger.error("Failed to send pre-medication notification to family: family LINE ID={}, error message={}", familyLineId, exception.getMessage());
            } else {
                logger.info("Pre-medication notification sent to family successfully: family LINE ID={}", familyLineId);
            }
        });
    }
}
