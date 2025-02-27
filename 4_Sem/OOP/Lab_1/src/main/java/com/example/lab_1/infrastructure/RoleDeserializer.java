package com.example.lab_1.infrastructure;

import com.example.lab_1.entities.Enums.Role;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RoleDeserializer extends JsonDeserializer<Map<String, Role>> {
    @Override
    public Map<String, Role> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Map<String, String> temp = p.readValueAs(Map.class);
        Map<String, Role> result = new HashMap<>();

        for (Map.Entry<String, String> entry : temp.entrySet()) {
            result.put(entry.getKey(), Role.valueOf(entry.getValue())); // Преобразование строки в Enum
        }

        return result;
    }
}
