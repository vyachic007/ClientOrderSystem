package by.slava_borisov.service_util;

import by.slava_borisov.helper.TransactionHelper;
import org.hibernate.SessionFactory;

public abstract class BaseService {

    protected final SessionFactory sessionFactory;
    protected final TransactionHelper transactionHelper;

    protected BaseService(SessionFactory sessionFactory, TransactionHelper transactionHelper) {
        this.sessionFactory = sessionFactory;
        this.transactionHelper = transactionHelper;
    }
}
