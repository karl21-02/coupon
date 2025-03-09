package com.kangwon.couponcore.repository.mysql;


import com.kangwon.couponcore.model.CouponIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponIssueJpaRepository extends JpaRepository<CouponIssue, Long> {
}
