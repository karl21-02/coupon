package com.kangwon.couponcore.service;

import com.kangwon.couponcore.exception.CouponIssueException;
import com.kangwon.couponcore.model.Coupon;
import com.kangwon.couponcore.model.CouponIssue;
import com.kangwon.couponcore.model.event.CouponIssueCompleteEvent;
import com.kangwon.couponcore.repository.mysql.CouponIssueJpaRepository;
import com.kangwon.couponcore.repository.mysql.CouponIssueRepository;
import com.kangwon.couponcore.repository.mysql.CouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kangwon.couponcore.exception.ErrorCode.COUPON_NOT_EXIST;
import static com.kangwon.couponcore.exception.ErrorCode.DUPLICATE_COUPON_ISSUE;

@RequiredArgsConstructor
@Service
public class CouponIssueService {

    private final CouponJpaRepository couponJpaRepository;
    private final CouponIssueJpaRepository couponIssueJpaRepository;
    private final CouponIssueRepository couponIssueRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void issue(long couponId, long userId) {
            Coupon coupon = findCoupon(couponId);
            coupon.issue(); // 쿠폰 발급( 검증 로직 포함 )
            saveCouponIssue(couponId, userId);
            publishCouponEvent(coupon);
    }

    private void publishCouponEvent(Coupon coupon) {
        if(coupon.isIssueComplete()) {
            applicationEventPublisher.publishEvent(new CouponIssueCompleteEvent(coupon.getId()));
        }
    }

    @Transactional(readOnly = true)
    public Coupon findCoupon(long couponId) {
        return couponJpaRepository.findById(couponId).orElseThrow(() -> {
            throw new CouponIssueException(COUPON_NOT_EXIST, "쿠폰 정책이 존재하지 않습니다. %s".formatted(couponId));
        });
    }

    @Transactional
    public Coupon findCouponWithLock(long couponId) {
        return couponJpaRepository.findCouponWithLock(couponId).orElseThrow(() -> {
            throw new CouponIssueException(COUPON_NOT_EXIST, "쿠폰 정책이 존재하지 않습니다. %s".formatted(couponId));
        });
    }

    @Transactional
    public CouponIssue saveCouponIssue(long couponId, long userId) {
        checkAlreadyIssuance(couponId, userId);
        CouponIssue couponIssue = CouponIssue.builder()
                .couponId(couponId)
                .userId(userId)
                .build();
        return couponIssueJpaRepository.save(couponIssue);
    }

    private void checkAlreadyIssuance(long couponId, long userId) {
        CouponIssue issue = couponIssueRepository.findFirstCouponIssue(couponId, userId);
        if (issue != null) {
            throw new CouponIssueException(DUPLICATE_COUPON_ISSUE, "이미 발급된 쿠폰입니다. user_id: %d, coupon_id: %d".formatted(userId, couponId));
        }
    }
//
//    private void publishCouponEvent(Coupon coupon) {
//        if (coupon.isIssueComplete()) {
//            applicationEventPublisher.publishEvent(new CouponIssueCompleteEvent(coupon.getId()));
//        }
//    }
}