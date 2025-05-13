package com.app.fineapp.model;

import com.app.fineapp.model.enums.AccountType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "accounts")
public class Account extends Icon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(precision = 19, scale = 4)
    private BigDecimal balance;

    private boolean includeToBalance;

    @OneToMany(
            mappedBy = "account",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Operation> operations = new ArrayList<>();

    @Version
    private Integer version;

    public Account() { }

    public Account(String title,
                   String description,
                   AccountType type,
                   BigDecimal balance,
                   boolean includeToBalance) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.balance = balance;
        this.includeToBalance = includeToBalance;
    }

    // ======= геттеры/сеттеры =======
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

    public List<Operation> getOperations() { return operations; }
    public void setOperations(List<Operation> operations) { this.operations = operations; }

    // удобные методы для управления связью
    public void addOperation(Operation op) {
        operations.add(op);
        op.setAccount(this);
    }
    public void removeOperation(Operation op) {
        operations.remove(op);
        op.setAccount(null);
    }

    public Integer getVersion() {
        return version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }

    // ======= equals/hashCode/toString =======
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        return Objects.equals(id, ((Account) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", balance=" + balance +
                ", includeToBalance=" + includeToBalance +
                ", image=" + image +
                ", color=" + color +
                '}';
    }
}
