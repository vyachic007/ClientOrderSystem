package by.slava_borisov.service;

import by.slava_borisov.entity.Client;
import by.slava_borisov.entity.Order;
import by.slava_borisov.entity.Status;
import by.slava_borisov.helper.TransactionHelper;
import by.slava_borisov.service_util.BaseService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    List<Order> findOrders(LocalDate orderDate, BigDecimal totalAmount, Status status);

    void addOrder(Client client, LocalDate orderDate, BigDecimal totalAmount, Status status);

    List<Client> findClientWithOrders();

}
