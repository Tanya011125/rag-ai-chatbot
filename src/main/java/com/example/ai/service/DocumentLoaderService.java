package com.example.ai.service;

import jakarta.annotation.PostConstruct;
import com.example.ai.model.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.ai.embedding.EmbeddingModel;
import com.example.ai.vectorstore.SimpleStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentLoaderService {
    
    private final EmbeddingModel embeddingModel;
    public DocumentLoaderService(EmbeddingModel embeddingModel){
        this.embeddingModel = embeddingModel;
    }

    @Autowired
    private SimpleStore simpleStore;
   
   @PostConstruct
    public void loadKnowledgeBase() throws IOException {
    // Load the file
    ClassPathResource resource = new ClassPathResource("knowledgebase/Aamantran_Citizen_KnowledgeBase.txt");
    String content = new String(resource.getInputStream().readAllBytes());

    // Simple splitting (by double newline or period)
    String[] chunks = content.split("\\. "); // or use "\\n\\n" for paragraph-based

    List<Document> documents = new ArrayList<>();
    for (String chunk : chunks) {
        documents.add(new Document(chunk));
    }

    for (int i = 0; i < documents.size(); i++) {
    Document doc = documents.get(i);
    float[] embedding = embeddingModel.embed(List.of(doc.getContent())).get(0);  // Get embedding
    simpleStore.add("doc-" + i, doc.getContent(), embedding);
}

    System.out.println("✅ Loaded " + documents.size() + " documents into vector store.");
}

}
