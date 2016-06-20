package by.it.academy.adorop.service.api;

import by.it.academy.adorop.service.exceptions.ServiceException;

import java.io.Serializable;
import java.util.List;

public interface Service<T, ID extends Serializable> {
    T find(ID id) throws ServiceException;
    Long getTotalCount() throws ServiceException;
    List<T> getBunch(int firstResult, int lastResult) throws ServiceException;
    T persist(T entity) throws ServiceException;
}
