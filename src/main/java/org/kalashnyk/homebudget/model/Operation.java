package org.kalashnyk.homebudget.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Sergii on 17.08.2016.
 */
@Entity
@Table(name = "operations")
@Data
@NoArgsConstructor
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
}