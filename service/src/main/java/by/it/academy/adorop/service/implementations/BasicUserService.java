package by.it.academy.adorop.service.implementations;

import by.it.academy.adorop.dao.api.UserDAO;
import by.it.academy.adorop.dao.exceptions.DaoException;
import by.it.academy.adorop.dao.utils.HibernateUtils;
import by.it.academy.adorop.model.users.User;
import by.it.academy.adorop.service.api.UserService;
import by.it.academy.adorop.service.exceptions.ServiceException;

public abstract class BasicUserService<T extends User> extends BasicService<T, Long> implements UserService<T> {

    UserDAO<T> userDAO;

    @Override
    public boolean isValid(String documentId, String password) throws ServiceException {
        try {
            transaction = HibernateUtils.beginTransaction();
            T retrievedUser = userDAO.getByDocumentId(documentId);
            transaction.commit();
            return !(retrievedUser == null || !retrievedUser.getPassword().equals(password));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public T getByDocumentId(String documentId) throws ServiceException {
        try {
            transaction = HibernateUtils.beginTransaction();
            T user = userDAO.getByDocumentId(documentId);
            transaction.commit();
            return user;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isAlreadyExists(String documentId) throws ServiceException {
        return getByDocumentId(documentId) != null;
    }
}
