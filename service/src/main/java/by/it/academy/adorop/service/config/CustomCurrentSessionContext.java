package by.it.academy.adorop.service.config;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.spi.CurrentSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class CustomCurrentSessionContext implements CurrentSessionContext {
//    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public Session currentSession() throws HibernateException {
        return sessionFactory.getCurrentSession();
    }
}
