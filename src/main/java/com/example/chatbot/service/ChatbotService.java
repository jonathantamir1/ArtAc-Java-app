package com.example.chatbot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ChatbotService {
    
    private static final Logger logger = Logger.getLogger(ChatbotService.class.getName());
    
    @Value("${chatbot.api.key}")
    private String apiKey;
    
    @Value("${chatbot.api.url:https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent}")
    private String apiUrl;
    
    private final RestTemplate restTemplate;
    
    public ChatbotService() {
        this.restTemplate = new RestTemplate();
    }
    
    public String processMessage(String userMessage) {
        try {
            if (userMessage == null || userMessage.trim().isEmpty()) {
                return "Please provide a valid message.";
            }
            
            String sanitizedMessage = sanitizeInput(userMessage);
            Map<String, Object> requestBody = createRequestBody(sanitizedMessage);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            
            String fullUrl = apiUrl + "?key=" + apiKey;
            logger.info("Calling Gemini API: " + fullUrl);
            ResponseEntity<Map> response = restTemplate.postForEntity(fullUrl, request, Map.class);
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return extractResponseText(response.getBody());
            } else {
                logger.warning("API call failed with status: " + response.getStatusCode());
                return "I'm sorry, I'm having trouble processing your request right now.";
            }
            
        } catch (Exception e) {
            logger.severe("Error processing message: " + e.getMessage());
            return "I apologize, but I encountered an error while processing your message.";
        }
    }
    
    private String sanitizeInput(String input) {
        String cleaned = input.replaceAll("[<>\\\"'&]", "").trim();
        if (cleaned.length() > 500) {
            cleaned = cleaned.substring(0, 500);
        }
        return cleaned;
    }
    
    private Map<String, Object> createRequestBody(String message) {
        Map<String, Object> part = new HashMap<>();
        part.put("text", message);

        Map<String, Object> content = new HashMap<>();
        content.put("role", "user");
        content.put("parts", List.of(part));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", List.of(content));
        return requestBody;
    }
    
    private String extractResponseText(Map<String, Object> responseBody) {
        try {
            Object candidates = responseBody.get("candidates");
            if (candidates instanceof java.util.List<?> candidatesList && !candidatesList.isEmpty()) {
                Object firstCandidate = candidatesList.get(0);
                if (firstCandidate instanceof Map<?, ?> candidateMapRaw) {
                    Map<String, Object> candidateMap = (Map<String, Object>) candidateMapRaw;
                    Object content = candidateMap.get("content");
                    if (content instanceof Map<?, ?> contentMapRaw) {
                        Map<String, Object> contentMap = (Map<String, Object>) contentMapRaw;
                        Object parts = contentMap.get("parts");
                        if (parts instanceof java.util.List<?> partsList && !partsList.isEmpty()) {
                            Object firstPart = partsList.get(0);
                            if (firstPart instanceof Map<?, ?> partMapRaw) {
                                Map<String, Object> partMap = (Map<String, Object>) partMapRaw;
                                Object text = partMap.get("text");
                                if (text instanceof String) {
                                    return (String) text;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.warning("Error extracting response text: " + e.getMessage());
        }
        return "I received a response but couldn't parse it properly.";
    }
}


