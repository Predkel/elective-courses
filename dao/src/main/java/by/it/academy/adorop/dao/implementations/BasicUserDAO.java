package by.it.academy.adorop.dao.implementations;

import by.it.academy.adorop.dao.api.UserDAO;
import by.it.academy.adorop.model.users.User;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public abstract class BasicUserDAO<T extends User> extends BasicDAO<T, Long> implements UserDAO <T>{


    public BasicUserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getByDocumentId(String documentId) {
        return (T) getBy(documentId, getPersistedClass());
    }

    @Override
    public boolean isAlreadyExists(String documentId) {
        return getBy(documentId, User.class) != null;
    }

    private Object getBy(String documentId, Class userType) {
        return currentSession().createCriteria(userType)
                .add(Restrictions.eq("documentId", documentId))
                .uniqueResult();
    }
}
