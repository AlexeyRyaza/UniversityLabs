package com.app.fineapp.service;

import com.app.fineapp.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.jsonlib.*;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Service
public class AutoAuthService {
    private final String PATH = "AuthorizedUsers/lastUser.json";
    private final JsonSerializer jsonSerializer = new JsonSerializer();
    private final JsonDeserializer jsonDeserializer = new JsonDeserializer();
    private final UserService userService;

    @Autowired
    public AutoAuthService(UserService userService) {
        this.userService = userService;
    }

    public CompletableFuture<Boolean> isUserExist() {
        try {
            File file = new File(PATH);

            if (!file.exists() || file.length() == 0) {
                return CompletableFuture.completedFuture(false);
            }


            String json = Files.readString(Path.of(PATH));
            if (json == null || json.isBlank()) {
                return CompletableFuture.completedFuture(false);
            }

            UserDTO userFromFile = jsonDeserializer.deserialize(json, UserDTO.class);

            if (userFromFile == null || userFromFile.getId() == 0) {
                return CompletableFuture.completedFuture(false);
            }

            return userService.getUserById(userFromFile.getId())
                    .thenApply(userFromDb ->
                            userFromDb.getEmail() != null &&
                                    userFromDb.getPassword() != null &&
                                    userFromDb.getUsername() != null
                    ).exceptionally(ex -> false);

        } catch (Exception e) {
            return CompletableFuture.completedFuture(false);
        }
    }

    public CompletableFuture<UserDTO> getUserFromFile() {
        Optional<UserDTO> userFromFileOpt = readUserFromJson();

        if (userFromFileOpt.isEmpty()) {
            return CompletableFuture.completedFuture(null);
        }

        UserDTO userFromFile = userFromFileOpt.get();

        return userService.getUserById(userFromFile.getId())
                .thenApply(userFromDb -> {
                    if (userFromDb.getEmail() == null ||
                            userFromDb.getUsername() == null ||
                            userFromDb.getPassword() == null) {
                        return null;
                    }
                    return userFromDb;
                })
                .exceptionally(ex -> {
                    return null;
                });
    }

    public CompletableFuture<Void> saveAuthorizedUser(UserDTO user) {
        return CompletableFuture.runAsync(() -> {
            try {
                String json = jsonSerializer.serialize(user);
                if (json == null || json.isBlank()) {
                    throw new IllegalStateException("Serialization failed");
                }

                File directory = new File("AuthorizedUsers");
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                Files.writeString(Path.of(PATH), json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public CompletableFuture<Void> deleteAuthorizedUser() {
        return CompletableFuture.runAsync(() -> {
            try {
                File file = new File(PATH);
                if (file.exists()) {
                    new FileWriter(file, false).close(); // Очистить содержимое файла
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private Optional<UserDTO> readUserFromJson() {
        try {
            File file = new File(PATH);
            if (!file.exists() || file.length() == 0) {
                return Optional.empty();
            }

            String json = Files.readString(Path.of(PATH));
            if (json == null || json.isBlank()) {
                return Optional.empty();
            }

            UserDTO user = jsonDeserializer.deserialize(json, UserDTO.class);

            if (user == null || user.getId() == 0) {
                return Optional.empty();
            }

            return Optional.of(user);

        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
