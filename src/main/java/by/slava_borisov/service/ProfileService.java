package by.slava_borisov.service;

import by.slava_borisov.helper.TransactionHelper;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final SessionFactory sessionFactory;
    private final TransactionHelper transactionHelper;

    public ProfileService(SessionFactory sessionFactory, TransactionHelper transactionHelper) {
        this.sessionFactory = sessionFactory;
        this.transactionHelper = transactionHelper;
    }

    public void addProfile(String address, String phone) {
        transactionHelper.executeInTransaction(session -> {
            session.createNativeQuery("""
                    INSERT INTO Profile (address, phone)
                    VALUES (:address, :phone)
                    """, Object.class)
                    .setParameter("address", address)
                    .setParameter("phone", phone)
                    .executeUpdate();
        });
    }

}
