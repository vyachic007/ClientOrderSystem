package by.slava_borisov.service_util;

import by.slava_borisov.helper.TransactionHelper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public abstract class BaseService {
    protected final SessionFactory sessionFactory;
    protected final TransactionHelper transactionHelper;
}
