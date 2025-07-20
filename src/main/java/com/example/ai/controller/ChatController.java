package com.example.ai.controller;

import com.example.ai.model.MessageRequest;
import com.example.ai.service.RagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private RagService ragService;

    @PostMapping
    public String chat(@RequestBody MessageRequest messageRequest) {
        return ragService.askQuestion(messageRequest.getMessage());
    }
}




