package com.medishare.service;

import com.linecorp.bot.client.LineMessagingClient;
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
}
