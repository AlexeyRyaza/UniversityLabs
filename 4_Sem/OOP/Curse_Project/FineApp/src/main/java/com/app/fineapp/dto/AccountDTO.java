package com.app.fineapp.dto;

import com.app.fineapp.model.enums.AccountType;
import java.math.BigDecimal;

public class AccountDTO {
    private Integer id;
    private String title;
    private String description;
    private AccountType type;
    private BigDecimal balance;
    private boolean includeToBalance;

    // добавляем поля от Icon
    private Integer image;
    private Integer color;

    // геттеры/сеттеры
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public AccountType getType() { return type; }
    public void setType(AccountType type) { this.type = type; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public boolean isIncludeToBalance() { return includeToBalance; }
    public void setIncludeToBalance(boolean includeToBalance) { this.includeToBalance = includeToBalance; }

    public Integer getImage() { return image; }
    public void setImage(Integer image) { this.image = image; }

    public Integer getColor() { return color; }
    public void setColor(Integer color) { this.color = color; }
}
