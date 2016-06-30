package by.it.academy.adorop.dao.implementations;

import by.it.academy.adorop.dao.api.UserDAO;
import by.it.academy.adorop.model.users.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public abstract class BasicUserDAO<T extends User> extends BasicDAO<T, Long> implements UserDAO <T>{


    public BasicUserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getByDocumentId(String documentId) {
        return (T) currentSession().createCriteria(getPersistedClass())
                .add(Restrictions.eq("documentId", documentId))
                .uniqueResult();
    }
}
