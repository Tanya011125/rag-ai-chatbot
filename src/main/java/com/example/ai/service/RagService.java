package com.example.ai.service;

import com.example.ai.vectorstore.SimpleStore;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RagService {

    private final SimpleStore simpleStore;
    private final EmbeddingModel embeddingModel;
    private final OllamaChatModel chatModel;

    public RagService(SimpleStore simpleStore, EmbeddingModel embeddingModel, OllamaChatModel chatModel) {
        this.simpleStore = simpleStore;
        this.embeddingModel = embeddingModel;
        this.chatModel = chatModel;
    }

    public String askQuestion(String question) {
        try{
        // Step 1: Embed the user question
        float[] questionEmbedding = embeddingModel.embed(List.of(question)).get(0);

        // Step 2: Search for relevant documents
        List<String> relevantChunks = simpleStore.search(questionEmbedding, 3);

        // Step 3: Build prompt
        StringBuilder prompt = new StringBuilder("Answer based on the following context:\n\n");
        for (String chunk : relevantChunks) {
            prompt.append("- ").append(chunk).append("\n");
        }
        prompt.append("\nQuestion: ").append(question);

        // Step 4: Get response from LLM
        return chatModel.call(prompt.toString());
        }catch (Exception e) {
        // Step 6: Handle exceptions
        System.err.println("Error generating answer: " + e.getMessage());
        return "⚠️I'm sorry, I don't have the information to answer this question. Please contact the support team at 011-23488XX.";
    }    
    }
}

