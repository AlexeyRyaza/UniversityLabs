package com.app.fineapp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OperationDTO {
    private Integer id;
    private Integer accountId;
    private Integer categoryId;
    private BigDecimal amount;
    private String comment;
    private boolean archived;
    private LocalDateTime date;

    // также поля от Icon
    private Integer image;
    private Integer color;

    // геттеры и сеттеры
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getAccountId() { return accountId; }
    public void setAccountId(Integer accountId) { this.accountId = accountId; }

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public boolean isArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public Integer getImage() { return image; }
    public void setImage(Integer image) { this.image = image; }

    public Integer getColor() { return color; }
    public void setColor(Integer color) { this.color = color; }
}
