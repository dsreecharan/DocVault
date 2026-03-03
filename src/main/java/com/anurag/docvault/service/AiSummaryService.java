package com.example.docvault.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AiSummaryService {

    @Value("${ai.api.url}")
    private String apiUrl;   // http://localhost:11434/api/generate

    @Value("${ai.model}")
    private String model;    // llama3

    private final RestTemplate restTemplate = new RestTemplate();

    public String summarize(String text) {

        // If Ollama not running or text empty
        if (text == null || text.isBlank()) {
            return "No content available to summarize.";
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> body = new HashMap<>();
            body.put("model", model);
            body.put("prompt", "Summarize in 3-5 sentences:\n\n" + text);
            body.put("stream", false);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<Map> response =
                    restTemplate.postForEntity(apiUrl, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return (String) response.getBody().get("response");
            }

            return "Summary unavailable.";

        } catch (Exception e) {
            return "Ollama summary failed: " + e.getMessage();
        }
    }
}