package com.example.tpo7.Services;

import com.example.tpo7.Models.Code;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class CodeService {
    private final Map<String, Long> codeStorage = new HashMap<>();
    private final File storageDir = new File("codeData");

    public CodeService() {
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
    }

    public void save(String id, String codeText, long durationSeconds) {
        long now = System.currentTimeMillis();
        long duration = durationSeconds * 1000;
        Code code = new Code(codeText, now, duration);

        File file = new File(storageDir, id + ".ser");

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(code);
            codeStorage.put(id, now + duration);
        } catch (IOException e) {
            throw new RuntimeException("Cannot save code: " + e.getMessage());
        }
    }

    public Code get(String id) {
        File file = new File(storageDir, id + ".ser");

        if (!file.exists()) return null;

        long now = System.currentTimeMillis();
        Long expiryTime = codeStorage.get(id);

        if (expiryTime != null && now > expiryTime) {
            file.delete();
            codeStorage.remove(id);
            return null;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (Code) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
