package com.example.lab_1.services;

import com.example.lab_1.entities.LogEntry;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LogService {
    private static final String LOG_FILE = "logs/app.log";
    private static final String KEY_FILE = "logs/key.secret";
    private static SecretKey secretKey;

    // Инициализация ключа
    static {
        try {
            Path path = Paths.get(KEY_FILE);
            if (Files.exists(path)) {
                byte[] keyBytes = Files.readAllBytes(path);
                secretKey = new SecretKeySpec(keyBytes, "AES");
            } else {
                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                keyGen.init(128);
                secretKey = keyGen.generateKey();
                Files.createDirectories(Paths.get("logs"));
                Files.write(path, secretKey.getEncoded());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logAction(LogEntry log) {
        try {
            if (!Files.exists(Paths.get(LOG_FILE))) {
                Files.createDirectories(Paths.get("logs"));
                Files.createFile(Paths.get(LOG_FILE));
            }

            try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
                writer.write(encrypt(log.toJson()) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<LogEntry> getAllRawLogs() {
        try {
            if (!Files.exists(Paths.get(LOG_FILE))) {
                return List.of();
            }

            return Files.lines(Paths.get(LOG_FILE))
                    .map(LogService::decrypt)
                    .map(LogEntry::fromJson)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public static List<LogEntry> getLogs() {
        return getAllRawLogs().stream()
                .filter(Objects::nonNull)
                .filter(log -> !log.reversed())
                .collect(Collectors.toList());
    }


    public static String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String decrypt(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decoded = Base64.getDecoder().decode(encryptedData);
            return new String(cipher.doFinal(decoded));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updateLogFile(LogEntry updatedLog) {
        List<LogEntry> logs = getAllRawLogs().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        for (int i = 0; i < logs.size(); i++) {
            if (logs.get(i).getTimestamp().equals(updatedLog.getTimestamp())) {
                logs.set(i, updatedLog);
                break;
            }
        }

        try (FileWriter writer = new FileWriter(LOG_FILE, false)) {
            for (LogEntry log : logs) {
                writer.write(encrypt(log.toJson()) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
