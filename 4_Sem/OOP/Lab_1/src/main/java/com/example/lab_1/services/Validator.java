package com.example.lab_1.services;

public class Validator {
    private Validator() {}

    public static boolean isValidPassport(String passport) {
        return passport != null && passport.matches("^[A-Z0-9]{8,10}$");
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    public static boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("^\\+\\d{11,15}$");
    }

     public static boolean isValidName(String name) {
        return name != null && name.matches("^[a-zA-Zа-яА-ЯёЁ\\s]+$");
    }
}
