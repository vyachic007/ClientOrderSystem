package by.slava_borisov.service;

import by.slava_borisov.entity.Coupon;
import by.slava_borisov.helper.TransactionHelper;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CouponService {

    private final SessionFactory sessionFactory;
    private final TransactionHelper transactionHelper;

    public CouponService(SessionFactory sessionFactory, TransactionHelper transactionHelper) {
        this.sessionFactory = sessionFactory;
        this.transactionHelper = transactionHelper;
    }

    public void updateCoupon(Long id, String code, BigDecimal discount) {
        transactionHelper.executeInTransaction(session -> {
            Coupon coupon = session.createQuery(
                            "SELECT c FROM Coupon c WHERE c.id = :id", Coupon.class)
                    .setParameter("id", id)
                    .uniqueResult();
            if (coupon != null) {
                coupon.setCode(code);
                coupon.setDiscount(discount);
                session.merge(coupon);
            } else {
                throw  new RuntimeException("Coupon with id " + id + " not found");
            }
        });
    }
}
