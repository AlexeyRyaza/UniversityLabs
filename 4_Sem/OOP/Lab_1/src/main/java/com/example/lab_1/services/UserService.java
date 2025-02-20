package com.example.lab_1.services;

import com.example.lab_1.entities.User;

public class UserService {
    private static UserService instance;
    private UserService() {}

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    //Methods
    //=======================================
    public User RegisterUser(String lastname, String firstname, String fathername,
                             String phone, String passport, String password, String email) {
        // TODO Get all users, if there are no such user (validate by password, email and phone) add new one anr retutn it
        // TODO ELSE return null
        if(true){
            return new User.Builder()
                    .lastName(lastname)
                    .firstName(firstname)
                    .fatherName(fathername)
                    .email(email)
                    .passport(passport)
                    .phone(phone)
                    .password(password)
                    .build();
        }
        else {
            return null;
        }

    }
}
