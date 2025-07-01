package com.medishare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String to, List<String> medicineNames, String medicationMethod) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("medishare.reporter@gmail.com");
        message.setTo(to);
        message.setSubject("【Medishare】 " + to + "さんの服薬レポート");
        message.setText(
            "以下の薬を服用しました。\n\n" +
            String.join("\n", medicineNames) + "\n\n" +
            "服用したタイミング・または時間: " + medicationMethod + "\n\n" +
            "このメールは自動送信されています。"
        );
        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send email to " + to + ": " + e.getMessage());
        }
    }
}
