package com.example.docvault.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class AiSummaryService {

    @Value("${ai.api.url}")  private String apiUrl;
    @Value("${ai.api.key}")  private String apiKey;
    @Value("${ai.model}")    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    public String summarize(String text) {
        if (apiKey == null || apiKey.equals("YOUR_OPENAI_API_KEY_HERE") || apiKey.isBlank()) {
            return generatePlaceholder(text);
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);
            Map<String, Object> body = Map.of(
                "model", model,
                "messages", List.of(Map.of(
                    "role", "user",
                    "content", "Summarize in 3-5 sentences:\n\n" + text
                )),
                "max_tokens", 300
            );
            HttpEntity<Map<String, Object>> req = new HttpEntity<>(body, headers);
            ResponseEntity<Map> resp = restTemplate.postForEntity(apiUrl, req, Map.class);
            if (resp.getStatusCode() == HttpStatus.OK && resp.getBody() != null) {
                var choices = (List<Map<String, Object>>) resp.getBody().get("choices");
                var msg = (Map<String, String>) choices.get(0).get("message");
                return msg.get("content");
            }
            return "Summary unavailable.";
        } catch (Exception e) {
            return "Summary generation failed: " + e.getMessage();
        }
    }

    private String generatePlaceholder(String text) {
        int words = text.split("\\s+").length;
        return String.format(
            "[AI PLACEHOLDER] ~%d words. Add your OpenAI key to application.properties.",
            words);
    }
}