package com.example.lab_1.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogEntry {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String actionType;
    private final String targetIds;
    private final String message;
    private final String timestamp;
    private boolean reversed;

    @JsonCreator
    public LogEntry(
            @JsonProperty("actionType") String actionType,
            @JsonProperty("targetIds") String targetIds,
            @JsonProperty("message") String message,
            @JsonProperty("timestamp") String timestamp,
            @JsonProperty("reversed") boolean reversed
    ) {
        this.actionType = actionType;
        this.targetIds = targetIds;
        this.message = message;
        this.timestamp = timestamp;
        this.reversed = reversed;
    }

    public LogEntry(String actionType, String targetIds, String message) {
        this(actionType, targetIds, message, LocalDateTime.now().format(formatter), false);
    }

    @JsonProperty public String getActionType() { return actionType; }
    @JsonProperty public String getTargetIds() { return targetIds; }
    @JsonProperty public String getMessage() { return message; }
    @JsonProperty public String getTimestamp() { return timestamp; }
    @JsonProperty public boolean reversed() { return reversed; }

    public void markReversed() { this.reversed = true; }

    public String toJson() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка сериализации LogEntry", e);
        }
    }

    public static LogEntry fromJson(String json) {
        try {
            return objectMapper.readValue(json, LogEntry.class);
        } catch (JsonProcessingException e) {
            System.err.println("Ошибка при разборе JSON лога: " + json);
            e.printStackTrace();
            return null;
        }
    }
}
