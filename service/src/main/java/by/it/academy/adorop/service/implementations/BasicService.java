package by.it.academy.adorop.service.implementations;

import by.it.academy.adorop.dao.api.DAO;
import by.it.academy.adorop.service.api.Service;
import javafx.util.Pair;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public abstract class BasicService<T, ID extends Serializable> implements Service<T, ID> {

    @Transactional
    public ID save(T entity) {
        return getDAO().save(entity);
    }

    public T find(ID id) {
        return getDAO().find(id);
    }

    @Override
    public List<T> findAll() {
        return getDAO().findAll();
    }

    public T findSingleResultBy(String propertyName, Object property) {
        return getDAO().findSingleResultBy(propertyName, property);
    }

    public T findSingleResultBy(Pair<String, Object> firstProperty, Pair<String, Object> secondProperty) {
        return getDAO().findSingleResultBy(firstProperty, secondProperty);
    }

    public T findSingleResultBy(Map<String, Object> properties) {
        return getDAO().findSingleResultBy(properties);
    }

    public List<T> findBy(String propertyName, Object property) {
        return getDAO().findBy(propertyName, property);
    }

    public List<T> findBy(Pair<String, Object> firstProperty, Pair<String, Object> secondProperty) {
        return getDAO().findBy(firstProperty, secondProperty);
    }

    public List<T> findBy(Map<String, Object> properties) {
        return getDAO().findBy(properties);
    }

    @Transactional
    public void update(T entity) {
        getDAO().update(entity);
    }

    @Transactional
    public T delete(T entity) {
        return getDAO().delete(entity);
    }

    @Override
    public Long getCount() {
        return getDAO().getCount();
    }

    @Override
    public List<T> getBunch(int firstResult, int maxResults) {
        return getDAO().getBunch(firstResult, maxResults);
    }

    protected abstract DAO<T, ID> getDAO();
}
