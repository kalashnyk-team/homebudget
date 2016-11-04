package org.kalashnyk.homebudget.repository.jpa;

import org.kalashnyk.homebudget.model.User;
import org.kalashnyk.homebudget.repository.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Sergii on 27.10.2016.
 */
@Repository
public class JpaUserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean delete(long id) {
        return em.createNamedQuery(User.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public List<User> getAll() {
        return em.createNamedQuery(User.GET_ALL, User.class)
                .getResultList();
    }

    @Override
    public User findById(long id) throws DataAccessException {
        return em.find(User.class, id);
    }

    @Override
    public User save(User user) throws DataAccessException {
        if (user.isNew()) {
            em.persist(user);
            return user;
        } else {
            return em.merge(user);
        }
    }
}
