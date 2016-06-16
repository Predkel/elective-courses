package by.it.academy.adorop.service.api;

import by.it.academy.adorop.model.users.User;
import by.it.academy.adorop.service.exceptions.ServiceException;

public interface UserService<T extends User> extends Service<T, Long> {
    boolean isValid(String documentId, String password) throws ServiceException;
    T getByDocumentId(String documentId) throws ServiceException;
    boolean isAlreadyExists(String documentId) throws ServiceException;
}
