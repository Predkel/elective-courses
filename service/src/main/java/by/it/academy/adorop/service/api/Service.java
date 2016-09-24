package by.it.academy.adorop.service.api;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface Service<T, ID extends Serializable> {
    ID save(T entity);
    T find(ID id);
    List<T> findAll();
    T findSingleResultBy(String propertyName, Object property);
    T findSingleResultBy(Pair<String, Object> firstProperty, Pair<String, Object> secondProperty);
    T findSingleResultBy(Map<String, Object> properties);
    List<T> findBy(String propertyName, Object property);
    List<T> findBy(Pair<String, Object> firstProperty, Pair<String, Object> secondProperty);
    List<T> findBy(Map<String, Object> properties);
    void update(T entity);
    T delete(T entity);
    boolean isAlreadyExists(T entity);
    Long getCount();
    List<T> getBunch(int firstResult, int maxResults);
}
