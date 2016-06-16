package by.it.academy.adorop.dao.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    private HibernateUtils() {
    }

    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new MetadataSources(new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build())
                    .buildMetadata()
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    public static Session getCurrentSession() {
        return getSessionFactory().getCurrentSession();
    }

    public static Transaction beginTransaction() {
        return getCurrentSession().beginTransaction();
    }

    public static void closeSessionFactory() {
        getSessionFactory().close();
    }
}
