package by.slava_borisov.service;

import java.math.BigDecimal;

public interface CouponService {

     void updateCoupon(Long id, String code, BigDecimal discount);
}
