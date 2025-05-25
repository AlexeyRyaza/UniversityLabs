package com.app.fineapp.dto;

public class RememberMeCredentials {
    private String encryptedEmail;
    private String encryptedPassword;

    public RememberMeCredentials() {
    }

    public RememberMeCredentials(String encryptedEmail, String encryptedPassword) {
        this.encryptedEmail = encryptedEmail;
        this.encryptedPassword = encryptedPassword;
    }

    public String getEncryptedEmail() {
        return encryptedEmail;
    }

    public void setEncryptedEmail(String encryptedEmail) {
        this.encryptedEmail = encryptedEmail;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}
