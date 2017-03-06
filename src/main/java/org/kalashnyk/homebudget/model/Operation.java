package org.kalashnyk.homebudget.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

/**
 * Created by Sergii on 17.08.2016.
 */
@Entity
@Table(name = "operations")
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = {"correspondingOperation"})
@EqualsAndHashCode(callSuper = true, exclude = {"correspondingOperation"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Operation extends BaseEntity implements Comparable<Operation>, Cloneable {
    @OneToOne
    @JoinColumn(name = "correspondent_id")
    private Operation correspondingOperation;

    @NotNull
    @Column(name = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private OperationCategory category;

    @NotNull
    @Column(name = "amount")
    private BigDecimal amount;

    @NotNull
    @Column(name = "amount_after")
    private BigDecimal remainOnAccount;

    @ManyToOne
    @JoinColumn(name = "acc_id")
    private Account account;

    @Column(name = "comment")
    private String comment;

    @Column(name = "amount_in_base_currency")
    private BigDecimal amountInBaseCurrency;

    @Builder
    public Operation(Long id, Operation correspondingOperation,
                     LocalDateTime date, OperationCategory category,
                     BigDecimal amount, Account account, String comment,
                     BigDecimal remainOnAccount, BigDecimal amountInBaseCurrency) {
        super(id);
        this.correspondingOperation = correspondingOperation;
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.account = account;
        this.comment = comment;
        this.remainOnAccount = remainOnAccount;
        this.amountInBaseCurrency = amountInBaseCurrency;
    }

    @Override
    public int compareTo(Operation anotherOperation) {
        if (this.date.compareTo(anotherOperation.date) == 0) {
            return this.id.compareTo(anotherOperation.id);
        } else {
            return this.date.compareTo(anotherOperation.date);
        }
    }

    public boolean isExpense() {
        return category.isExpense();
    }

    public String description() {
        switch (category.getOperationType()) {
            case IN_TRANSFER:
            case OUT_TRANSFER:
                return category.getName() + " '" + this.correspondingOperation.account.name + "'";
            default:
                return category.toString();
        }
    }

    public Operation clone() throws CloneNotSupportedException {
        return (Operation) super.clone();
    }

    public Currency getCurrency() {
        return account.getCurrency();
    }

    public Currency baseCurrency() {
        return account.getOwner().getBasicCurrency();
    }
}