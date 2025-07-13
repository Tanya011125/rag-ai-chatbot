package com.example.ai.vectorstore;

import java.util.List;

public interface SimpleStore {
    void add(String id, String content, float[] embedding);
    List<String> search(float[] queryEmbedding, int topK);
}
