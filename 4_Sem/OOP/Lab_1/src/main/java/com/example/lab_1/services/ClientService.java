package com.example.lab_1.services;

import com.example.lab_1.entities.User;

public class ClientService {
    private static ClientService instance;

    private ClientService() {}

    public static synchronized ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }

        return instance;
    }

    //Methods
    //=========================================================
    public void CreateRegistrationRequest(User user) {

    }
}
