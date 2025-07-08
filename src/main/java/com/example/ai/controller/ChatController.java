package com.example.ai.controller;

import com.example.ai.model.MessageRequest;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private OllamaChatModel chatModel;

    @PostMapping
    public String chat(@RequestBody MessageRequest messageRequest) {
        return chatModel.call(messageRequest.getMessage());
    }
}




