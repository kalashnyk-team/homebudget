package org.kalashnyk.homebudget.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Sergii on 17.08.2016.
 */
@Entity
@Table(name = "operations")
@NoArgsConstructor
@Getter
@Setter
public class Operation extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "correspondent_id")
    private Operation correspondingOperation;

    @NotNull
    @Column(name = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime date;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private OperationCategory category;

    @NotNull
    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "acc_id")
    private Account account;

    @Column(name = "description")
    private String description;

    @Builder
    public Operation(Long id, Operation correspondingOperation,
                     LocalDateTime date, OperationCategory category,
                     BigDecimal amount, Account account, String description) {
        super(id);
        this.correspondingOperation = correspondingOperation;
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.account = account;
        this.description = description;
    }
}