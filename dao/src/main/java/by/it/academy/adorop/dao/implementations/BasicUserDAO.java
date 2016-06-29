package by.it.academy.adorop.dao.implementations;

import by.it.academy.adorop.dao.api.UserDAO;
import by.it.academy.adorop.model.users.User;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public abstract class BasicUserDAO<T extends User> extends BasicDAO<T, Long> implements UserDAO <T>{

    public BasicUserDAO(Session session) {
        super(session);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getByDocumentId(String documentId) {
        return (T) session.createCriteria(getPersistedClass())
                .add(Restrictions.eq("documentId", documentId))
                .uniqueResult();
    }
}
