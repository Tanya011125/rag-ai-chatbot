package com.example.ai;

import com.example.ai.service.DocumentLoaderService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements ApplicationRunner {

    private final DocumentLoaderService documentLoaderService;

    public StartupRunner(DocumentLoaderService documentLoaderService) {
        this.documentLoaderService = documentLoaderService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        documentLoaderService.loadKnowledgeBase();
    }
}
