package com.example.ai.vectorstore;

import java.util.List;

public interface VectorStore {
    void add(String id, String content, float[] embedding);
    List<String> search(float[] embedding, int topK);
}
