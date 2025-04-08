package com.kangwon.couponcore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kangwon.couponcore.component.DistrubuteLockExecutor;
import com.kangwon.couponcore.repository.redis.RedisRepository;
import com.kangwon.couponcore.repository.redis.dto.CouponRedisEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsyncCouponIssueServiceV1 {
    private final RedisRepository redisRepository;
    private final CouponIssueRedisService couponIssueRedisService;
//    private final DistrubuteLockExecutor distrubuteLockExecutor;

    private final CouponCacheService couponCacheService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void issue(long couponId, long userId) {
        CouponRedisEntity coupon = couponCacheService.getCouponLocalCache(couponId);
        coupon.checkIssueableCoupon();
        issueRequest(couponId, userId, coupon.totalQuantity()); // 큐에 발급 요청 적재
    }

    private void issueRequest(long couponId, long userId, Integer totalIssueQnantity) {
        if(totalIssueQnantity == null) {
            redisRepository.issueRequest(couponId, userId, Integer.MAX_VALUE);

        }
        redisRepository.issueRequest(couponId, userId, totalIssueQnantity);
    }
}