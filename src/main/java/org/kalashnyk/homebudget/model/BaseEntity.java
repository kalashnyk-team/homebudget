package org.kalashnyk.homebudget.model;

import javax.persistence.*;

/**
 * Created by Sergii on 16.08.2016.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class BaseEntity {
    @Id
    @GeneratedValue
    protected Long id;

    public BaseEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isNew() {
        return id == null;
    }
}
