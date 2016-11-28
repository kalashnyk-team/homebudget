package org.kalashnyk.homebudget.model;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
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
@Data
@ToString(exclude = "password")
public class User extends NamedEntity{
    public static final String DELETE = "User.delete";
    public static final String GET_ALL = "User.getAll";

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

    @OneToMany(mappedBy = "owner")
    private List<Account> accounts;

    @ElementCollection(targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<Role> roles;
}