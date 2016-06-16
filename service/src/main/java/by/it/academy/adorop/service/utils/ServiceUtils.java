package by.it.academy.adorop.service.utils;

import by.it.academy.adorop.dao.utils.HibernateUtils;

public class ServiceUtils {
    private ServiceUtils() {
    }

    public static void releaseResources() {
        HibernateUtils.closeSessionFactory();
    }
}
