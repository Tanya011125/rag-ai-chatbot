package com.example.ai.config;

import com.example.ai.vectorstore.InMemoryVectorStore;
import com.example.ai.vectorstore.SimpleStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VectorStoreConfig {

    @Bean
    public SimpleStore simpleStore() {
        return new InMemoryVectorStore();
    }
}
