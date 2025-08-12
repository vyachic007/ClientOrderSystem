package by.slava_borisov.config;

import by.slava_borisov.entity.Client;
import by.slava_borisov.entity.Coupon;
import by.slava_borisov.entity.Order;
import by.slava_borisov.entity.Profile;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfiguration {

    @Bean
    public static SessionFactory getSessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();

        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres");
        configuration.setProperty("hibernate.connection.username", "postgres");
        configuration.setProperty("hibernate.connection.password", "root");

        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.format_sql", "true");
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");

        configuration.addAnnotatedClass(Client.class);
        configuration.addAnnotatedClass(Profile.class);
        configuration.addAnnotatedClass(Order.class);
        configuration.addAnnotatedClass(Coupon.class);

        return configuration.buildSessionFactory();
    }
}
