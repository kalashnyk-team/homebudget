package org.kalashnyk.homebudget.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

/**
 * Created by Sergii on 17.08.2016.
 */
@Entity
@Table(name = "accounts")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account extends NamedEntity implements Comparable<Account> {
    @NotNull
    @Column(name = "currency_code")
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
        return Arrays.asList(Type.values());
    }

    @Override
    public int compareTo(Account anotherAccount) {
        if (this.type.compareTo(anotherAccount.type) != 0) {
            return this.type.compareTo(anotherAccount.type);
        } else {
            return this.name.compareTo(anotherAccount.name);
        }
    }

    public enum Type implements Comparable<Type> {
        CASH,
        DEBIT_CARD,
        CREDIT_CARD,
        DEPOSIT,
        DEBT;
    }
}