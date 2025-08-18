package com.example.chatbot.controller;

import com.example.chatbot.model.ChatMessage;
import com.example.chatbot.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ChatController {
    
    @Autowired
    private ChatbotService chatbotService;
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("chatMessage", new ChatMessage());
        return "index";
    }
    
    @PostMapping("/api/chat")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> chat(@Valid @RequestBody ChatMessage chatMessage, 
                                                   BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();
        
        if (bindingResult.hasErrors()) {
            response.put("error", "Invalid input: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            String botResponse = chatbotService.processMessage(chatMessage.getMessage());
            response.put("response", botResponse);
            response.put("timestamp", System.currentTimeMillis());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "An error occurred while processing your message.");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}


