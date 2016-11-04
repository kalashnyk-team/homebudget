package org.kalashnyk.homebudget.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Sergii on 17.08.2016.
 */
@Entity
@Table(name = "categories")
public class OperationCategory extends NamedEntity {

    @NotEmpty
    @Column(name = "type")
    private OperationType operationType;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    public OperationCategory() {
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType type) {
        this.operationType = type;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
