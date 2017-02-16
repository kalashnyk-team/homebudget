package org.kalashnyk.homebudget.model;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Sergii on 17.08.2016.
 */
@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@Setter
public class OperationCategory extends NamedEntity {
    @NotEmpty
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private OperationCategory parent;

    @OneToMany(mappedBy = "parent")
    private List<OperationCategory> subCategories;

    @Builder
    private OperationCategory(Long id, String name, OperationType operationType, User owner, OperationCategory parent) {
        super(id, name);
        this.operationType = operationType;
        this.owner = owner;
        this.parent = parent;
    }

    public enum OperationType {
        INCOME,
        EXPENSE,
        IN_TRANSFER,
        OUT_TRANSFER;
    }

    public String toString() {
        if (parent == null)
            return name;
        else
            return parent.toString() + ": " + name;
    }
}
