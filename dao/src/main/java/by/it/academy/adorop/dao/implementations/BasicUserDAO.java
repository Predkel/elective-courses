package by.it.academy.adorop.dao.implementations;

import by.it.academy.adorop.dao.api.UserDAO;
import by.it.academy.adorop.dao.exceptions.DaoException;
import by.it.academy.adorop.model.users.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import static by.it.academy.adorop.dao.utils.HibernateUtils.getCurrentSession;

public abstract class BasicUserDAO<T extends User> extends BasicDAO<T, Long> implements UserDAO <T>{

    @Override
    @SuppressWarnings("unchecked")
    public T getByDocumentId(String documentId) throws DaoException {
        try {
            Session session = getCurrentSession();
            return (T) session.createCriteria(getPersistedClass())
                    .add(Restrictions.eq("documentId", documentId))
                    .uniqueResult();
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }
}
