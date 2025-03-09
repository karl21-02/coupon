package com.kangwon.couponcore.repository.mysql;

import com.kangwon.couponcore.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {
}
