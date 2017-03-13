package org.kalashnyk.homebudget.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

/**
 * Created by Sergii on 16.08.2016.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    BaseEntity(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public boolean isNew() {
        return id == null;
    }
}
