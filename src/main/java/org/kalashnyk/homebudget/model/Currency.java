package org.kalashnyk.homebudget.model;

import lombok.*;

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
@EqualsAndHashCode(of = {"id"})
public class Currency {
    public static final Currency UAH = Currency.builder().id(980).name("UAH").build();
    public static final Currency USD = Currency.builder().id(840).name("USD").build();
    public static final Currency EUR = Currency.builder().id(978).name("EUR").build();
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "char_id")
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "variableCurrency")
    @Singular
    private List<FXRate> rates;

    @Builder
    private Currency(int id, String name, List<FXRate> rates) {
        this.id = id;
        this.name = name;
        this.rates = rates;
    }

    @Override
    public String toString() {
        return name;
    }
}
