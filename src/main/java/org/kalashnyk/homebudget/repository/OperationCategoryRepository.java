package org.kalashnyk.homebudget.repository;

import org.kalashnyk.homebudget.model.OperationCategory;

import java.util.List;

/**
 * Created by Sergii on 18.08.2016.
 */
public interface OperationCategoryRepository {

    OperationCategory findById(long id, long userId);

    OperationCategory save(OperationCategory category, long userId);

    boolean delete(long id, long userId);

    List<OperationCategory> getAll(long userId);

    OperationCategory getServiceCategory(String serviceCategory);
}
