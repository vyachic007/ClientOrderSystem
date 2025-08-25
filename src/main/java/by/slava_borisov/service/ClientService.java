package by.slava_borisov.service;

import by.slava_borisov.entity.Client;
import by.slava_borisov.entity.Profile;
import by.slava_borisov.helper.TransactionHelper;
import by.slava_borisov.service_util.BaseService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

public interface ClientService {

     void addClient(String name, String email, String address, String phone);

     void removeClient(Long id);

     Client getClientById(Long id);

}

