package by.it.academy.adorop.dao.implementations;

import by.it.academy.adorop.dao.api.DAO;
import by.it.academy.adorop.dao.exceptions.DaoException;
import by.it.academy.adorop.dao.utils.CatchAndRethrow;
import javafx.util.Pair;
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

    @SuppressWarnings("unchecked")
    public ID save(T entity) {
        return (ID) currentSession().save(entity);
    }

    public T find(ID id) {
        return currentSession().get(persistedClass(), id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return currentSession().createCriteria(persistedClass()).list();
    }

    @SuppressWarnings("unchecked")
    public T findSingleResultBy(String propertyName, Object property) {
        return (T) createCriteriaWithSingleRestriction(propertyName, property)
                .uniqueResult();
    }

    private Criteria createCriteriaWithSingleRestriction(String propertyName, Object property) {
        return currentSession().createCriteria(persistedClass())
                .add(Restrictions.eq(propertyName, property));
    }

    @SuppressWarnings("unchecked")
    public T findSingleResultBy(Pair<String, Object> firstProperty, Pair<String, Object> secondProperty) {
        return (T) createCriteriaWithDoubleRestriction(firstProperty, secondProperty)
                .uniqueResult();
    }

    private Criteria createCriteriaWithDoubleRestriction(Pair<String, Object> firstProperty, Pair<String, Object> secondProperty) {
        return currentSession().createCriteria(persistedClass())
                .add(Restrictions.eq(firstProperty.getKey(), firstProperty.getValue()))
                .add(Restrictions.eq(secondProperty.getKey(), secondProperty.getValue()));
    }

    @SuppressWarnings("unchecked")
    public T findSingleResultBy(Map<String, Object> properties) {
        createCriteriaWithMultipleRestrictions(properties);
        return (T) createCriteriaWithMultipleRestrictions(properties).uniqueResult();
    }

    private Criteria createCriteriaWithMultipleRestrictions(Map<String, Object> properties) {
        Criteria criteria = currentSession().createCriteria(persistedClass());
        Set<Map.Entry<String, Object>> entries = properties.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String propertyName = entry.getKey();
            String[] namesOfNestedProperties = propertyName.split("\\.");
            int length = namesOfNestedProperties.length;
            if (!propertyName.contains(".")) {
                criteria.add(Restrictions.eq(propertyName, entry.getValue()));
            } else {
//                String associationPath = propertyName.substring(0, propertyName.lastIndexOf("."));
//                String alias = associationPath.replace(".", "_");
                Criteria subCriteria = criteria.createCriteria(namesOfNestedProperties[0]);
                for (int i = 1; i < length - 1; i++) {
                    subCriteria = subCriteria.createCriteria(namesOfNestedProperties[i]);
                }
                String restrictingProperty = namesOfNestedProperties[length - 1];
                subCriteria.add(Restrictions.eq(restrictingProperty, entry.getValue()));
//                criteria.createAlias(associationPath, alias);
//                criteria.add(Restrictions.sqlRestriction(propertyName + "=" + entry.getValue().toString()));
//                String alias = namesOfNestedProperties[0];
//                criteria.createAlias(alias, alias);
//                for (int i = 1; i < length - 1; i++) {
////                    alias += "." + namesOfNestedProperties[i];
//                    String associationPath = namesOfNestedProperties[i-1] + "." + namesOfNestedProperties[i];
//                    alias = namesOfNestedProperties[i-1] + "_" + namesOfNestedProperties[i];
////                    alias = namesOfNestedProperties[i];
//                    criteria.createAlias(associationPath, alias);
//                }
//                String lastNameOfNestedProperty = namesOfNestedProperties[length -2] + "." + namesOfNestedProperties[length - 1];
////                criteria.add(Restrictions.eq(lastNameOfNestedProperty, entry.getValue()));
//                String restrictingProperty = namesOfNestedProperties[length - 2] + "." + namesOfNestedProperties[length - 1];
//                criteria.add(Restrictions.eq(restrictingProperty, entry.getValue()));
            }
        }
        return criteria;
    }

    @SuppressWarnings("unchecked")
    public List<T> findBy(String propertyName, Object property) {
        return createCriteriaWithSingleRestriction(propertyName, property).list();
    }
    @SuppressWarnings("unchecked")
    public List<T> findBy(Pair<String, Object> firstProperty, Pair<String, Object> secondProperty) {
        return createCriteriaWithDoubleRestriction(firstProperty, secondProperty).list();
    }
    @SuppressWarnings("unchecked")
    public List<T> findBy(Map<String, Object> properties) {
        return createCriteriaWithMultipleRestrictions(properties).list();
    }

    @Override
    public void update(T entity) {
        currentSession().update(entity);
    }

    @Override
    public T delete(T entity) {
        currentSession().delete(entity);
        return entity;
    }

    @Override
    public Long getCount() {
        List result = currentSession().createCriteria(persistedClass())
                .setProjection(Projections.rowCount())
                .list();
        return  !result.isEmpty() ? (Long) result.get(0) : 0L;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getBunch(int firstResult, int maxResult) {
        return currentSession()
                .createCriteria(persistedClass())
                .setFirstResult(firstResult)
                .setMaxResults(maxResult)
                .list();
    }
    protected abstract Class<T> persistedClass();
}
