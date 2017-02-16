package org.kalashnyk.homebudget.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Sergii on 17.08.2016.
 */
@Entity
@Table(name = "currencies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "char_id")
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "variableCurrency")
    private List<FXRate> rates;

    @Override
    public String toString() {
        return name;
    }
}
