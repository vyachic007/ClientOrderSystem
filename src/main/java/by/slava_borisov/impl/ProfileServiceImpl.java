package by.slava_borisov.impl;

import by.slava_borisov.entity.Profile;
import by.slava_borisov.helper.TransactionHelper;
import by.slava_borisov.service.ProfileService;
import by.slava_borisov.service_util.BaseService;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl extends BaseService implements ProfileService {

    public ProfileServiceImpl(SessionFactory sessionFactory, TransactionHelper transactionHelper) {
        super(sessionFactory, transactionHelper);
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
