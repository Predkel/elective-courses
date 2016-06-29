package by.it.academy.adorop.service.implementations;

import by.it.academy.adorop.dao.api.UserDAO;
import by.it.academy.adorop.model.users.User;
import by.it.academy.adorop.service.api.UserService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public abstract class BasicUserService<T extends User> extends BasicService<T, Long> implements UserService<T> {

    UserDAO<T> userDAO;

    @Override
    public boolean isValid(String documentId, String password) {
        T retrievedUser = userDAO.getByDocumentId(documentId);
        return !(retrievedUser == null || !retrievedUser.getPassword().equals(password));
    }

    @Override
    public T getByDocumentId(String documentId) {
        return userDAO.getByDocumentId(documentId);
    }

    @Override
    public boolean isAlreadyExists(String documentId) {
        return getByDocumentId(documentId) != null;
    }
}
