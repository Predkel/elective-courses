package by.it.academy.adorop.dao.api;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface DAO<T, ID extends Serializable> {
    T persist(T entity);
    T get(ID id);
    T update(T entity);
    void delete(T entity);
    List<T> getAll();
    Long getCount();
    List<T> getBunch(int firstResult, int maxResult);
    List<T> getBy(String propertyName, Object value);
    List<T> getBy(Map<String, Object> propertiesNameToValues);
}
