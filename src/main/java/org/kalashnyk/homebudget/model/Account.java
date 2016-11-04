package org.kalashnyk.homebudget.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Sergii on 17.08.2016.
 */
@Entity
@Table(name = "accounts")
public class Account extends NamedEntity {
    @Enumerated(EnumType.STRING)
    @NotEmpty
    @Column(name = "currency")
    private Currency currency;

    @NotEmpty
    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @OneToMany(mappedBy = "debitAccount")
    private List<Operation> incomes;

    @OneToMany(mappedBy = "creditAccount")
    private List<Operation> expenses;

    @Enumerated(EnumType.STRING)
    @NotEmpty
    @Column(name = "type")
    private AccountType type;

    public Account(String name, Currency currency, BigDecimal amount, User owner, AccountType type) {
        this.name = name;
        this.currency = currency;
        this.amount = amount;
        this.owner = owner;
        this.type = type;
    }

    public Account() {
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal money) {
        this.amount = money;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Operation> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<Operation> incomes) {
        this.incomes = incomes;
    }

    public List<Operation> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Operation> expenses) {
        this.expenses = expenses;
    }
}
