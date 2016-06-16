package by.it.academy.adorop.dao.api;

import by.it.academy.adorop.dao.exceptions.DaoException;
import by.it.academy.adorop.model.users.User;

public interface UserDAO<T extends User> extends DAO<T, Long> {
    T getByDocumentId(String documentId) throws DaoException;
}
