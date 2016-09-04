package by.it.academy.adorop.service.implementations;

import by.it.academy.adorop.dao.api.UserDAO;
import by.it.academy.adorop.model.users.User;
import by.it.academy.adorop.service.api.UserService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public abstract class BasicUserService<T extends User> extends BasicService<T, Long> implements UserService<T> {

    final UserDAO<T> userDAO;

    public BasicUserService(UserDAO<T> userDAO) {
        this.userDAO = userDAO;
    }


    @Override
    public boolean isAlreadyExists(T user) {
        return !userDAO.getFromAllUsersBy("documentId", user.getDocumentId()).isEmpty();
    }
}
