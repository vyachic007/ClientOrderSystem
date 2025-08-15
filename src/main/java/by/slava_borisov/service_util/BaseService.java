package by.slava_borisov.service_util;

import by.slava_borisov.helper.TransactionHelper;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;

@RequiredArgsConstructor
public abstract class BaseService {

    protected final SessionFactory sessionFactory;
    protected final TransactionHelper transactionHelper;

}
