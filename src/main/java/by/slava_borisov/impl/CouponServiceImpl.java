package by.slava_borisov.impl;

import by.slava_borisov.entity.Coupon;
import by.slava_borisov.helper.TransactionHelper;
import by.slava_borisov.service.CouponService;
import by.slava_borisov.service_util.BaseService;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CouponServiceImpl extends BaseService implements CouponService {

    public CouponServiceImpl(SessionFactory sessionFactory, TransactionHelper transactionHelper) {
        super(sessionFactory, transactionHelper);
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
