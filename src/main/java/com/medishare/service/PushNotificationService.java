package com.medishare.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PushNotificationService {
     @Value("${onesignal.api-key}")
    private String apiKey;

    @PostMapping("/send_notification")
    public void sendNotification(@RequestParam String message) {
        String url = "https://onesignal.com/api/v1/notifications";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = """
                {
                    "app_id": "ec99f1ff-95c6-4f17-beaf-320ad7d2b5fa",
                    "included_segments": ["Subscribed Users"],
                    "contents": {"ja": "%s"},
                }
                """.formatted(message);

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        System.out.println(response.getBody());
    }
}
