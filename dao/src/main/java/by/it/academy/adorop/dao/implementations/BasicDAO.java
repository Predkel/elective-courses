package by.it.academy.adorop.dao.implementations;

import by.it.academy.adorop.dao.api.DAO;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

public abstract class BasicDAO<T, ID extends Serializable> implements DAO<T, ID> {

    protected final Session session;

    public BasicDAO(Session session) {
        this.session = session;
    }

    @Override
    public T persist(T entity) {
        session.save(entity);
        return entity;
    }

    @Override
    public T get(ID id) {
        return session.get(getPersistedClass(), id);
    }

    @Override
    public T update(T entity) {
        session.update(entity);
        return entity;
    }

    @Override
    public void delete(T entity) {
        session.delete(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return session.createCriteria(getPersistedClass()).list();
    }

    @Override
    public Long getCount() {
        return (Long) session.createCriteria(getPersistedClass())
                .setProjection(Projections.rowCount())
                .list()
                .get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getBunch(int firstResult, int maxResult) {
        return session.createCriteria(getPersistedClass())
                .setFirstResult(firstResult)
                .setMaxResults(maxResult)
                .list();
    }

    protected abstract Class<T> getPersistedClass();
}
