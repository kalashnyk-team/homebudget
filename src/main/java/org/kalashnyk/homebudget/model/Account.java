package org.kalashnyk.homebudget.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Sergii on 17.08.2016.
 */
@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
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
    private Type type;

    public enum Type {
        CASH,
        DEBIT_CARD,
        CREDIT_CARD,
        DEPOSIT,
        DEBT;
    }
}