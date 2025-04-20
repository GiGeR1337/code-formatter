package com.example.tpo7.Services;

import com.example.tpo7.Models.Code;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CodeService {
    private final Map<String, Code> codeStorage = new HashMap<>();

    public void save(String id, String codeText, long durationSeconds) {
        long now = System.currentTimeMillis();
        long ttl = durationSeconds * 1000;
        Code code = new Code(codeText, now, ttl);
        codeStorage.put(id, code);
    }

    public Code get(String id) {
        Code snippet = codeStorage.get(id);
        if (snippet == null) return null;

        long now = System.currentTimeMillis();
        boolean expired = now - snippet.getTimestamp() > snippet.getTtl();

        if (expired) {
            codeStorage.remove(id);
            return null;
        }
        return snippet;
    }

    @PostConstruct
    public void startThread() {
        Thread cleaner = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);

                    long now = System.currentTimeMillis();

                    for (String id : codeStorage.keySet()) {
                        Code code = codeStorage.get(id);
                        if (code != null && now - code.getTimestamp() > code.getTtl()) {
                            codeStorage.remove(id);
                        }
                    }

                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        cleaner.setDaemon(true);
        cleaner.start();
    }
}
