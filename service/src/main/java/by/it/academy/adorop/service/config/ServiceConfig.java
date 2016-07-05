package by.it.academy.adorop.service.config;

import by.it.academy.adorop.dao.config.PersistenceConfig;
import org.hibernate.SessionFactory;
import org.hibernate.context.spi.CurrentSessionContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("by.it.academy.adorop.service")
@Import(PersistenceConfig.class)
public class  ServiceConfig {

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
//        transactionManager.setHibernateManagedSession(true);
        return transactionManager;
    }

//    @Bean
//    public CurrentSessionContext currentSessionContext() {
//        return new CustomCurrentSessionContext();
//    }
}
