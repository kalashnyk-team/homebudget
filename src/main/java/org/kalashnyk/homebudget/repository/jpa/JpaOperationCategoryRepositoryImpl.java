package org.kalashnyk.homebudget.repository.jpa;

import org.kalashnyk.homebudget.model.OperationCategory;
import org.kalashnyk.homebudget.model.User;
import org.kalashnyk.homebudget.repository.OperationCategoryRepository;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sergii on 25.08.2016.
 */
@Repository
@Transactional
public class JpaOperationCategoryRepositoryImpl implements OperationCategoryRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public OperationCategory findById(long id, long userId) {
        List<OperationCategory> categories = em.createQuery("SELECT c FROM OperationCategory c WHERE c.id=:id AND c.owner.id=:userId", OperationCategory.class)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .getResultList();
        return DataAccessUtils.singleResult(categories);
    }

    @Override
    public OperationCategory save(OperationCategory category, long userId) {
        if (!category.isNew() && findById(category.getId(), userId) == null) {
            return null;
        }
        category.setOwner(em.getReference(User.class, userId));
        if (category.isNew()) {
            em.persist(category);
            return category;
        } else {
            return em.merge(category);
        }
    }

    @Override
    public boolean delete(long id, long userId) {
        return em.createQuery("DELETE FROM OperationCategory c WHERE c.id=:id AND c.owner.id=:userId")
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public List<OperationCategory> getAll(long userId) {
        return em.createQuery("SELECT c FROM OperationCategory c WHERE c.owner.id=:userId", OperationCategory.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
