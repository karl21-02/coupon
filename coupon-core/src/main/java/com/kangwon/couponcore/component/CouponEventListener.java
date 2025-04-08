//package com.kangwon.couponcore.component;
//
//import com.kangwon.couponcore.model.event.CouponIssueCompleteEvent;
//import com.kangwon.couponcore.service.CouponCacheService;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.event.TransactionPhase;
//import org.springframework.transaction.event.TransactionalEventListener;
//
//@RequiredArgsConstructor
//@Component
//public class CouponEventListener {
//
//    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
//    private final CouponCacheService couponCacheService;
//
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
//    void issueComplete(CouponIssueCompleteEvent event) {
//        log.info("issue complet. cache refrest start couponId : %s".formatted(event.couponId()));
//        couponCacheService.putCouponCache(event.couponId());
//        couponCacheService.putCouponLocalCache(event.couponId());
//        log.info("issue complet. cache refrest end couponId : %s".formatted(event.couponId()));
//    }
//}
