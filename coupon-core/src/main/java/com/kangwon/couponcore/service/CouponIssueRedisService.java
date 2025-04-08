package com.kangwon.couponcore.service;

import com.kangwon.couponcore.exception.CouponIssueException;
import com.kangwon.couponcore.exception.ErrorCode;
import com.kangwon.couponcore.repository.redis.RedisRepository;
import com.kangwon.couponcore.repository.redis.dto.CouponRedisEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.kangwon.couponcore.util.CouponRedisUtils.getIssueRequestKey;

@RequiredArgsConstructor
@Service
public class CouponIssueRedisService {
    private final RedisRepository redisRepository;

    public void checkCouponIssueQuantity(CouponRedisEntity couponRedisEntity, long userId) {
        if(!availableTotalIssueQuantity(couponRedisEntity.totalQuantity(), couponRedisEntity.id())) { // 쿠폰 발급 수량 검증( 전체 발급 수량 대비 현재 발급 가능?)
            throw new CouponIssueException(ErrorCode.INVALID_COUPON_ISSUE_QUANTITY, "발급 가능한 수량을 초과합니다. couponId: %s, userId: %s");
        }
        if(!availableUserIssueQuantity(couponRedisEntity.id(), userId)) { // 중복 쿠폰 발급 검증
            throw new CouponIssueException(ErrorCode.DUPLICATE_COUPON_ISSUE, "이미 발급 요청이 처리됐습니다. couponId: %s, userId: %s");
        }
    }

    /**
     * 현재 레디스 안의 요소와 전체 발급 수량 비교
     * true -> 넣을 수 있음 == 쿠폰 발급 가능
     * @param totalQuantity
     * @param couponId
     * @return
     */
    public boolean availableTotalIssueQuantity(Integer totalQuantity, long couponId) {
        if(totalQuantity == null) {
            return true;
        }
        String key = getIssueRequestKey(couponId);
        return totalQuantity > redisRepository.sCard(key);
    }

    /**
     * 유저의 쿠폰 중복 발급 검증
     * @param couponId
     * @param userId
     * @return
     */
    public boolean availableUserIssueQuantity(long couponId, long userId) {
        String key = getIssueRequestKey(couponId);
        return !redisRepository.sIsMember(key, String.valueOf(userId));
    }
}
