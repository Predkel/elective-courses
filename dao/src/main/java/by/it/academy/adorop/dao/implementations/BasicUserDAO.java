package by.it.academy.adorop.dao.implementations;

import by.it.academy.adorop.dao.api.UserDAO;
import by.it.academy.adorop.model.users.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Map;

public abstract class BasicUserDAO<T extends User> extends BasicDAO<T, Long> implements UserDAO <T>{

    public BasicUserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getFromAllUsersBy(Map<String, Object> propertiesNameToValues) {
        Criteria criteria = currentSession().createCriteria(User.class);
        addRestrictions(propertiesNameToValues,criteria);
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getFromAllUsersBy(String propertyName, Object value) {
        return currentSession().createCriteria(User.class)
                .add(Restrictions.eq(propertyName, value))
                .list();
    }
}
