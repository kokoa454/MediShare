package com.medishare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    public void sendMail(String userName, String userEmail ,String to, List<String> medicineNames, String medicationMethod, String userCondition, String userComment) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("medishare.reporter@gmail.com");
        message.setTo(to);
        message.setSubject("【Medishare】" + userName + "(" + userEmail + ")" + "さんの服薬レポートをお届けします");
        message.setText(
            userName + "さんの服薬状況をお知らせいたします。\n\n" +
            "■ 服用したお薬\n" +
            String.join("\n", medicineNames) + "\n\n" +
            "■ 飲んだタイミング・時間\n" +
            medicationMethod + "\n\n" +
            "■ 体調のご様子\n" +
            userCondition + "\n\n" +
            "■ コメント\n" +
            userComment + "\n\n" +
            "Medishareがご家族の皆さまの安心につながれば幸いです。\n" +
            "本メールは自動送信です。ご不明点がございましたら、アプリ開発者(hm-c24036@sist.ac.jpかhm-c24063@sist.ac.jp)へご相談ください。"
        );
        try {
            mailSender.send(message);
            logger.info("Medication report email sent successfully: family email address={}", to);
        } catch (MailException e) {
            logger.error("Failed to send medication report email: family email address={}, error message={}", to, e.getMessage());
        }
    }

    public void sendPasswordResetEmail(String to, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("medishare.reporter@gmail.com");
        message.setTo(to);
        message.setSubject("【Medishare】パスワードリセット");
        message.setText("こちらのURLからパスワードをリセットしてください。\n" + resetLink + "\n" + "リンクの有効期限は1時間です。\n" + "また、本メールは自動送信のため、ご返信はお控えください。");
        try {
            mailSender.send(message);
            logger.info("Password reset email sent successfully: user email address={}", to);
        } catch (MailException e) {
            logger.error("Failed to send password reset email: user email address={}, error message={}", to, e.getMessage());
        }
    }
}
