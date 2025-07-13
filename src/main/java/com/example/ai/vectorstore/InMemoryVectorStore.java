package com.example.ai.vectorstore;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryVectorStore implements SimpleStore {

    private static class Entry {
        String id;
        String content;
        float[] embedding;

        Entry(String id, String content, float[] embedding) {
            this.id = id;
            this.content = content;
            this.embedding = embedding;
        }
    }

    private final List<Entry> store = new ArrayList<>();

    @Override
    public void add(String id, String content, float[] embedding) {
        store.add(new Entry(id, content, embedding));
    }

    @Override
    public List<String> search(float[] queryEmbedding, int topK) {
        return store.stream()
                .sorted(Comparator.comparingDouble(entry -> -cosineSimilarity(queryEmbedding, entry.embedding)))
                .limit(topK)
                .map(entry -> entry.content)
                .collect(Collectors.toList());
    }

    private double cosineSimilarity(float[] a, float[] b) {
        double dot = 0.0, normA = 0.0, normB = 0.0;
        for (int i = 0; i < a.length; i++) {
            dot += a[i] * b[i];
            normA += a[i] * a[i];
            normB += b[i] * b[i];
        }
        return dot / (Math.sqrt(normA) * Math.sqrt(normB) + 1e-10); // avoid divide by zero
    }
}
