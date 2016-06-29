package by.it.academy.adorop.service.api;

import java.io.Serializable;
import java.util.List;

public interface Service<T, ID extends Serializable> {
    T find(ID id);
    Long getTotalCount();
    List<T> getBunch(int firstResult, int lastResult);
    T persist(T entity);
}
