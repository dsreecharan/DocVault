public String summarize(String text) {

    Map<String, Object> body = new HashMap<>();
    body.put("model", model);   // llama3
    body.put("prompt",
            "You are an expert document summarizer. " +
                    "Summarize the following document clearly in 3–5 concise sentences:\n\n" + text);
    body.put("stream", false);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Map<String, Object>> request =
            new HttpEntity<>(body, headers);

    ResponseEntity<Map> response =
            restTemplate.postForEntity(apiUrl, request, Map.class);

    return response.getBody().get("response").toString();
}