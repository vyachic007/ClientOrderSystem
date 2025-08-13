package by.slava_borisov.service;

import by.slava_borisov.entity.Client;
import by.slava_borisov.entity.Profile;
import by.slava_borisov.helper.TransactionHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ClientService {

    private final SessionFactory sessionFactory;
    private final TransactionHelper transactionHelper;

    public ClientService(SessionFactory sessionFactory, TransactionHelper transactionHelper) {
        this.sessionFactory = sessionFactory;
        this.transactionHelper = transactionHelper;
    }

    public void addClient(String name, String email, String address, String phone) {
        transactionHelper.executeInTransaction(session -> {
            Client client = Client.builder()
                    .name(name)
                    .email(email)
                    .registrationDate(LocalDate.now())
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

    public void removeClient(Long id) {
        transactionHelper.executeInTransaction(session -> {
            Client client = session.get(Client.class, id);
            if (client != null) {
                session.remove(client);
            } else {
                throw new RuntimeException("Клиент с id " + id + " не найден");
            }
        });
    }

    public Client getClientById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT c FROM Client c WHERE c.id = :id", Client.class)
                    .setParameter("id", id)
                    .uniqueResult();
        } catch (Exception e) {
            throw  new RuntimeException("Ошибка. Клиент с id " + id + " не найден", e);
        }
    }

}

