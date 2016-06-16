package by.it.academy.adorop.dao.api;

import by.it.academy.adorop.dao.exceptions.DaoException;

import java.io.Serializable;
import java.util.List;

public interface DAO<T, ID extends Serializable> {
    T persist(T entity) throws DaoException;
    T get(ID id) throws DaoException;
    T update(T entity) throws DaoException;
    void delete(T entity) throws DaoException;
    List<T> getAll() throws DaoException;
    Long getCount() throws DaoException;
    List<T> getBunch(int firstResult, int maxResult) throws DaoException;
}
