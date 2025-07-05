package com.medishare.service;

import java.util.List;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import lombok.RequiredArgsConstructor;

@LineMessageHandler
@RequiredArgsConstructor
public class LineService {
    private final LineMessagingClient lineMessagingClient;

    @EventMapping
    public void handleFollowEvent(FollowEvent followEvent) {
        String userId = followEvent.getSource().getUserId();
        String replyToken = followEvent.getReplyToken();
        
        String message = "あなたのLINEのユーザーIDは以下の通りです。\n" + userId;
        
        lineMessagingClient.replyMessage(new ReplyMessage(
            replyToken, 
            new TextMessage(message))
        ).whenComplete((response, exception) -> {
            if (exception != null) {
                System.out.println( exception.getMessage());
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
                System.out.println( exception.getMessage());
            }
        });
    }

    public void notifyBeforeMedicationLine(String userLineId, String message) {
        
    }
}
