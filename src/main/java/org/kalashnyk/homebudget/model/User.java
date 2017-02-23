package org.kalashnyk.homebudget.model;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Sergii on 17.08.2016.
 */
@NamedQueries({
        @NamedQuery(name = User.DELETE, query = "DELETE FROM User u WHERE u.id=:id"),
        @NamedQuery(name = User.GET_ALL, query = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles"),
})

@Entity
@Table(name = "users")
@ToString(exclude = {"password", "roles", "accounts", "categories"})
@NoArgsConstructor
@Getter
@Setter
public class User extends NamedEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String DELETE = "User.delete";
    public static final String GET_ALL = "User.getAll";

    @NotNull
    @ManyToOne
    @JoinColumn(name = "basic_currency_id")
    private Currency basicCurrency;

    @NotEmpty
    @Column(name = "email")
    private String email;

    @NotEmpty
    @Column(name = "password")
    private String password;

    @NotEmpty
    @Column(name = "registered")
    private Date registered;

    @NotEmpty
    @Column(name = "confirmed")
    private boolean confirmed;

    @NotEmpty
    @Column(name = "enabled")
    private boolean enabled;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private List<Account> accounts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private List<OperationCategory> categories;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<Role> roles;

    @Builder
    private User(Long id, String name, Currency basicCurrency,
                 String email, String password, Date registered,
                 boolean confirmed, boolean enabled) {
        super(id, name);
        this.basicCurrency = basicCurrency;
        this.email = email;
        this.password = password;
        this.registered = registered;
        this.confirmed = confirmed;
        this.enabled = enabled;
    }
}