package com.app.fineapp.dto;

import java.util.List;

public class UserDTO {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private List<Integer> accountIds;
    private List<Integer> categoryIds;

    public UserDTO() {
    }

    public UserDTO(Integer id, String username, String email, String password, List<Integer> accountIds, List<Integer> categoryIds) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.accountIds = accountIds;
        this.categoryIds = categoryIds;
    }

    // ======= геттеры и сеттеры =======

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public List<Integer> getAccountIds() {
        return accountIds;
    }
    public void setAccountIds(List<Integer> accountIds) {
        this.accountIds = accountIds;
    }

    public List<Integer> getCategoryIds() {
        return categoryIds;
    }
    public void setCategoryIds(List<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }
}
