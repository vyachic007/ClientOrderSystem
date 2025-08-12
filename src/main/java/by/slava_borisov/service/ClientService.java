package by.slava_borisov.service;

import by.slava_borisov.entity.Client;
import by.slava_borisov.entity.Profile;
import by.slava_borisov.helper.TransactionHelper;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ClientService {

    private final SessionFactory sessionFactory;
    private final TransactionHelper transactionHelper;
    private final Profile profile;

    public ClientService(SessionFactory sessionFactory, TransactionHelper transactionHelper, Profile profile) {
        this.sessionFactory = sessionFactory;
        this.transactionHelper = transactionHelper;
        this.profile = profile;
    }

    public void addClient(String name, String email, String address, String phone) {
        transactionHelper.executeInTransaction(session -> {
            Client client = Client.builder()
                    .name(name)
                    .email(email)
                    .registrationDate(LocalDateTime.now())
                    .build();

            Profile profile = Profile.builder()
                    .address(address)
                    .phone(phone)
                    .client(client)
                    .build();

            client.setProfile(profile);
            session.persist(client);
        });
    }

    public void removeClient(Client client) {
        transactionHelper.executeInTransaction(session -> {
            session.remove(client);
        });
    }
}
