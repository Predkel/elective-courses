package by.it.academy.adorop.dao.implementations;

import by.it.academy.adorop.dao.api.DAO;
import by.it.academy.adorop.dao.exceptions.DaoException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import java.io.Serializable;
import java.util.List;

import static by.it.academy.adorop.dao.utils.HibernateUtils.getCurrentSession;

public abstract class BasicDAO<T, ID extends Serializable> implements DAO<T, ID> {

    @Override
    public T persist(T entity) throws DaoException {
        try {
            Session session = getCurrentSession();
            session.save(entity);
            return entity;
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public T get(ID id) throws DaoException {
        try {
            Session session = getCurrentSession();
            return session.get(getPersistedClass(), id);
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public T update(T entity) throws DaoException {
        try {
            Session session = getCurrentSession();
            session.update(entity);
            return entity;
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(T entity) throws DaoException {
        try {
            Session session = getCurrentSession();
            session.delete(entity);
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getAll() throws DaoException {
        try {
            Session session = getCurrentSession();
            return session.createCriteria(getPersistedClass()).list();
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Long getCount() throws DaoException {
        try {
            Session session = getCurrentSession();
            return (Long) session.createCriteria(getPersistedClass())
                    .setProjection(Projections.rowCount())
                    .list()
                    .get(0);
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getBunch(int firstResult, int maxResult) throws DaoException {
        try {
            Session session = getCurrentSession();
            return session.createCriteria(getPersistedClass())
                    .setFirstResult(firstResult)
                    .setMaxResults(maxResult)
                    .list();
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    protected abstract Class<T> getPersistedClass();
}
