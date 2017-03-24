package org.kalashnyk.homebudget.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperationCategory extends NamedEntity implements Comparable<OperationCategory> {
    public static final String IN_TRANSFER = ("IN_TRANSFER");
    public static final String OUT_TRANSFER = ("OUT_TRANSFER");
    public static final String OPENING = ("OPENING");

    @NotNull
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToOne(optional = true)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private OperationCategory parent;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private List<OperationCategory> subCategories;

    @Column(name = "level")
    private Integer level;


    @Builder
    private OperationCategory(Long id, int level, String name, OperationType operationType, User owner, OperationCategory parent) {
        super(id, name);
        this.level = level;
        this.operationType = operationType;
        this.owner = owner;
        this.parent = parent;
    }

    @Override
    public int compareTo(OperationCategory o) {
        if (operationType.ordinal() - o.getOperationType().ordinal() != 0) {
            return operationType.ordinal() - o.getOperationType().ordinal();
        } else if (!level.equals(o.getLevel())){
            return level.compareTo(o.getLevel());
        } else {
            return name.compareTo(o.getName());
        }
    }

    public enum OperationType {
        INCOME,
        EXPENSE,
        IN_TRANSFER,
        OUT_TRANSFER,
        OPENING,
        REMAIN_CHANGING;
    }

    @Override
    public String toString() {
        if (parent == null) {
            return name;
        } else {
            return parent + ": " + name;
        }
    }

    public boolean isExpense() {
        switch (operationType) {
            case OUT_TRANSFER:
            case EXPENSE:
                return true;
            case REMAIN_CHANGING:
            case IN_TRANSFER:
            case OPENING:
                return false;
            default:
                return false;
        }
    }
}
