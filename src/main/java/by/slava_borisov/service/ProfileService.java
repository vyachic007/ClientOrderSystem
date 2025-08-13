package by.slava_borisov.service;

import by.slava_borisov.entity.Profile;
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

    public void updateProfile(Long id, String newAddress, String newPhone) {
        transactionHelper.executeInTransaction(session -> {
            Profile profile = session.createQuery("SELECT p FROM Profile p WHERE p.id = :id", Profile.class)
                    .setParameter("id", id)
                    .uniqueResult();
           if (profile != null) {
               if (newAddress != null) profile.setAddress(newAddress);
               if (newPhone != null) profile.setPhone(newPhone);
               session.merge(profile);
           }
        });
    }
}
