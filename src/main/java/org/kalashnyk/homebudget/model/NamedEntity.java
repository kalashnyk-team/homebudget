package org.kalashnyk.homebudget.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by Sergii on 17.08.2016.
 */
@MappedSuperclass
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NamedEntity extends BaseEntity {
    @NotEmpty
    @Column(name = "name")
    protected String name;

    NamedEntity(Long id, String name) {
        super(id);
        this.name = name;
    }
}