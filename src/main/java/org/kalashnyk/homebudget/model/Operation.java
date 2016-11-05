package org.kalashnyk.homebudget.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Sergii on 17.08.2016.
 */
@Entity
@Table(name = "operations")
public class Operation extends BaseEntity {
    @NotEmpty
    @Column(name = "date")
    private LocalDateTime date;

    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "category_id")
    private OperationCategory category;

    @NotEmpty
    @Column(name = "amount")
    private BigDecimal amount;

    @NotEmpty
    @Column(name = "currency")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "credit_acc_id")
    private Account creditAccount;

    @ManyToOne
    @JoinColumn(name = "debit_acc_id")
    private Account debitAccount;

    @Column(name = "description")
    private String description;

    public Operation() {
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public OperationCategory getCategory() {
        return category;
    }

    public void setCategory(OperationCategory category) {
        this.category = category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Account getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(Account creditAccount) {
        this.creditAccount = creditAccount;
    }

    public Account getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(Account debitAccount) {
        this.debitAccount = debitAccount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}