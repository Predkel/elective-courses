package by.it.academy.adorop.dao.implementations;

import by.it.academy.adorop.dao.api.DAO;
import by.it.academy.adorop.dao.exceptions.DaoException;
import by.it.academy.adorop.dao.utils.CatchAndRethrow;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CatchAndRethrow(exceptionToCatch = RuntimeException.class, rethrow = DaoException.class)
public abstract class BasicDAO<T, ID extends Serializable> implements DAO<T, ID> {

    private final SessionFactory sessionFactory;

    public BasicDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public T persist(T entity) {
        currentSession().save(entity);
        return entity;
    }

    @Override
    public T get(ID id) {
        Session session = currentSession();
        return session.get(getPersistedClass(), id);
    }

    @Override
    public T update(T entity) {
        currentSession().update(entity);
        return entity;
    }

    @Override
    public void delete(T entity) {
        currentSession().delete(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return currentSession().createCriteria(getPersistedClass()).list();
    }

    @Override
    public Long getCount() {
        return (Long) currentSession().createCriteria(getPersistedClass())
                .setProjection(Projections.rowCount())
                .list()
                .get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getBunch(int firstResult, int maxResult) {
        return currentSession()
                .createCriteria(getPersistedClass())
                .setFirstResult(firstResult)
                .setMaxResults(maxResult)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getBy(String propertyName, Object value) {
        return currentSession().createCriteria(getPersistedClass())
                .add(Restrictions.eq(propertyName, value))
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getBy(Map<String, Object> propertiesNameToValues) {
        Criteria criteria = currentSession().createCriteria(getPersistedClass());
        addRestrictions(propertiesNameToValues, criteria);
        return criteria.list();
    }

    void addRestrictions(Map<String, Object> propertiesNameToValues, Criteria criteria) {
        Set<Map.Entry<String, Object>> entries = propertiesNameToValues.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
        }
    }

    protected abstract Class<T> getPersistedClass();
}
