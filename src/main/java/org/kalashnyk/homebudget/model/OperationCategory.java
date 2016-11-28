package org.kalashnyk.homebudget.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Sergii on 17.08.2016.
 */
@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
public class OperationCategory extends NamedEntity {

    @NotEmpty
    @Column(name = "type")
    private OperationType operationType;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    public enum OperationType {
        INCOME,
        EXPENSE,
        TRANSFER;
    }
}
