package by.slava_borisov.config;

import by.slava_borisov.entity.Client;
import by.slava_borisov.entity.Coupon;
import by.slava_borisov.entity.Order;
import by.slava_borisov.entity.Profile;
import by.slava_borisov.helper.TransactionHelper;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class HibernateConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public SessionFactory getSessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();

        configuration.setProperty("hibernate.connection.driver_class", env.getProperty("db.driver"));
        configuration.setProperty("hibernate.connection.url", env.getProperty("db.url"));
        configuration.setProperty("hibernate.connection.username", env.getProperty("db.username"));
        configuration.setProperty("hibernate.connection.password", env.getProperty("db.password"));

        configuration.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql", "true"));
        configuration.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql", "true"));
        configuration.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        configuration.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto", "update"));

        configuration.addAnnotatedClass(Client.class);
        configuration.addAnnotatedClass(Profile.class);
        configuration.addAnnotatedClass(Order.class);
        configuration.addAnnotatedClass(Coupon.class);

        return configuration.buildSessionFactory();
    }

    @Bean
    public TransactionHelper transactionHelper() {
        return new TransactionHelper(getSessionFactory());
    }
}