package by.it.academy.adorop.service.api;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface Service<T, ID extends Serializable> {
    List<T> getAll();
    T find(ID id);
    Long getTotalCount();
    List<T> getBunch(int firstResult, int maxResult);
    T persist(T entity);
    void update(T entity);
    boolean isAlreadyExists(T entity);
    T getSingleResultBy(String nameOfUniqueProperty, Object value);
    T getSingleResultBy(Map<String, Object> namesOfUniquePropertiesToValues);
}
