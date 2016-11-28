package org.kalashnyk.homebudget.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by Sergii on 16.08.2016.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@Data
@NoArgsConstructor
public class BaseEntity {
    @Id
    @GeneratedValue
    protected Long id;

    public boolean isNew() {
        return id == null;
    }
}
