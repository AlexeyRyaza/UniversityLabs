package com.app.fineapp.model;

import com.app.fineapp.model.enums.CategoryType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "operations")
public class Operation extends Icon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(precision = 19, scale = 4)
    private BigDecimal amount;

    private String comment;
    private boolean archived;
    private LocalDateTime date;

    @Version
    private Integer version;

    public Operation() { }

    public Operation(Account account,
                     Category category,
                     BigDecimal amount,
                     String comment,
                     boolean archived,
                     LocalDateTime date) {
        this.account = account;
        this.category = category;
        this.amount = amount;
        this.comment = comment;
        this.archived = archived;
        this.date = date;
    }

    // ======= геттеры/сеттеры =======

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public boolean isArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    // ======= equals/hashCode/toString =======

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operation)) return false;
        return Objects.equals(id, ((Operation) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", accountId=" + (account != null ? account.getId() : null) +
                ", categoryId=" + (category != null ? category.getId() : null) +
                ", amount=" + amount +
                ", comment='" + comment + '\'' +
                ", archived=" + archived +
                ", date=" + date +
                ", image=" + image +
                ", color=" + color +
                ", version=" + version +
                '}';
    }
}
