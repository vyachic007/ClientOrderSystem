package by.slava_borisov.service;

import by.slava_borisov.entity.Client;
import by.slava_borisov.entity.Order;
import by.slava_borisov.entity.Status;
import by.slava_borisov.helper.TransactionHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    private final SessionFactory sessionFactory;
    private final TransactionHelper transactionHelper;

    public OrderService(SessionFactory sessionFactory, TransactionHelper transactionHelper) {
        this.sessionFactory = sessionFactory;
        this.transactionHelper = transactionHelper;
    }

    public List<Order> findOrders(LocalDate orderDate, BigDecimal totalAmount, Status status) {
        try (Session session = sessionFactory.openSession()) {
            StringBuilder queryBuilder = new StringBuilder("SELECT o FROM Order o WHERE 1=1 ");

            if (orderDate != null) {
                queryBuilder.append(" AND o.orderDate = :orderDate");
            }
            if (totalAmount != null) {
                queryBuilder.append(" AND o.totalAmount = :totalAmount");
            }
            if (status != null) {
                queryBuilder.append(" AND o.status = :status");
            }

            var query = session.createQuery(queryBuilder.toString(), Order.class);
            if (orderDate != null) {
                query.setParameter("orderDate", orderDate);
            }
            if (totalAmount != null) {
                query.setParameter("totalAmount", totalAmount);
            }
            if (status != null) {
                query.setParameter("status", status);
            }

            return  query.getResultList();
        } catch (Exception e) {
            throw  new RuntimeException("Error finding orders: " + e.getMessage(), e);
        }
    }

    public void addOrder(Client client, LocalDate orderDate, BigDecimal totalAmount, Status status) {
        transactionHelper.executeInTransaction(session -> {
            Client managedClient = session.get(Client.class, client.getId());
            if (managedClient != null) {
                Order order = Order.builder()
                        .orderDate(orderDate)
                        .client(managedClient)
                        .totalAmount(totalAmount)
                        .status(status)
                        .build();

                managedClient.getOrders().add(order);

                session.persist(order);
            } else {
                throw new RuntimeException("Client with id " + client.getId() + " not found");
            }
        });
    }

    public List<Client> findClientWithOrders() {
        try (Session session = sessionFactory.openSession()) {
           return session.createQuery("""
                    SELECT DISTINCT c FROM Client c
                    LEFT JOIN FETCH c.orders
                    """, Client.class)
                    .getResultList();
        } catch (Exception e) {
            throw  new RuntimeException("Error finding clients with orders: " + e.getMessage(), e);
        }
    }

}
