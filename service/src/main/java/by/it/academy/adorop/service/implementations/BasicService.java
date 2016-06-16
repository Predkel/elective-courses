package by.it.academy.adorop.service.implementations;

import by.it.academy.adorop.dao.api.DAO;
import by.it.academy.adorop.dao.exceptions.DaoException;
import by.it.academy.adorop.dao.utils.HibernateUtils;
import by.it.academy.adorop.service.api.Service;
import by.it.academy.adorop.service.exceptions.ServiceException;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;

public abstract class BasicService<T, ID extends Serializable> implements Service<T, ID> {

    Transaction transaction;

    @Override
    public T find(ID id) throws ServiceException {
        try {
            transaction = HibernateUtils.beginTransaction();
            T entity = getDAO().get(id);
            transaction.commit();
            return entity;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Long getTotalCount() throws ServiceException {
        try {
            transaction = HibernateUtils.beginTransaction();
            Long count = getDAO().getCount();
            transaction.commit();
            return count;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<T> getBunch(int firstResult, int maxResult) throws ServiceException {
        try {
            transaction = HibernateUtils.beginTransaction();
            List<T> bunch = getDAO().getBunch(firstResult, maxResult);
            transaction.commit();
            return bunch;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public T save(T entity) throws ServiceException {
        try {
            transaction = HibernateUtils.beginTransaction();
            T savedEntity = getDAO().persist(entity);
            transaction.commit();
            return savedEntity;
        } catch (DaoException e) {
            return catchDaoException(e);
        }
    }

    T catchDaoException(DaoException daoException) throws ServiceException {
        if (transaction != null) {
            try {
                transaction.rollback();
            } catch (Exception e) {
                throw new ServiceException(e);
            }
        }
        throw new ServiceException(daoException);
    }

    protected abstract DAO<T, ID> getDAO();
}
