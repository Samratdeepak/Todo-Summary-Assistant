package com.example.todosummary.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;

@Service
public class LlmService {
    @Value("${cohere.api.key}")
    private String apiKey;

    @Value("${cohere.api.url}")
    private String apiUrl;

    public String summarizeTodos(List<String> todos) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String prompt = "Summarize these todos:\n" + String.join("\n", todos);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "command-r-plus"); // or "command", "command-r" etc., as per your Cohere access
        body.put("prompt", prompt);
        body.put("max_tokens", 100);
        body.put("temperature", 0.7);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonBody = mapper.writeValueAsString(body);
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);
            Map respBody = response.getBody();
            List generations = (List) respBody.get("generations");
            Map firstGen = (Map) generations.get(0);
            return (String) firstGen.get("text");
        } catch (Exception e) {
            throw new RuntimeException("Failed to get summary from Cohere", e);
        }
    }
}