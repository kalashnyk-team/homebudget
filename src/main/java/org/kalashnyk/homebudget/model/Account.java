package org.kalashnyk.homebudget.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergii on 17.08.2016.
 */
@Entity
@Table(name = "accounts")
@NoArgsConstructor
@Getter
@Setter
public class Account extends NamedEntity {
    @NotNull
    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @NotNull
    @Column(name = "amount")
    private BigDecimal amount;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User owner;

    @OneToMany(mappedBy = "account")
    @JsonIgnore
    private List<Operation> operations;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "type")
    private Type type;

    @Builder
    private Account(Long id, String name, Currency currency,
                    BigDecimal amount, User owner, Type type) {
        super(id, name);
        this.currency = currency;
        this.amount = amount;
        this.owner = owner;
        this.type = type;
    }

    public static List<Type> types() {
        val types = new ArrayList<Type>();

        for (Type type : Type.values())
            if (type != Type.EXPENSE && type != Type.INCOME)
                types.add(type);

        return types;
    }

    public enum Type {
        CASH,
        DEBIT_CARD,
        CREDIT_CARD,
        DEPOSIT,
        DEBT,
        EXPENSE,
        INCOME;
    }
}