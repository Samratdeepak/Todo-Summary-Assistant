package com.example.todosummary.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class SlackService {
    @Value("${slack.webhook.url}")
    private String webhookUrl;

    public boolean sendSummary(String summary) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(webhookUrl, Map.of("text", summary), String.class);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}