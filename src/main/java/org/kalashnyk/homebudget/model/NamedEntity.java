package org.kalashnyk.homebudget.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by Sergii on 17.08.2016.
 */
@MappedSuperclass
public class NamedEntity extends BaseEntity {
    @NotEmpty
    @Column(name = "name")
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
