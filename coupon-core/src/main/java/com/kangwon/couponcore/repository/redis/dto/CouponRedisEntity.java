package com.kangwon.couponcore.repository.redis.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.kangwon.couponcore.exception.CouponIssueException;
import com.kangwon.couponcore.exception.ErrorCode;
import com.kangwon.couponcore.model.Coupon;
import com.kangwon.couponcore.model.CouponType;

import java.time.LocalDateTime;

public record CouponRedisEntity (
    Long id,
    CouponType couponType,
    Integer totalQuantity,
    boolean availableIssueQuantity,

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime dateIssueStart,

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime dateIssueEnd
) {
    public CouponRedisEntity(Coupon coupon) {
        this(
                coupon.getId(),
                coupon.getCouponType(),
                coupon.getTotalQuantity(),
                coupon.availableIssueQuantity(),
                coupon.getDateIssueStart(),
                coupon.getDateIssueEnd()
        );
    }

    private boolean availableIssueDate() {
        LocalDateTime now = LocalDateTime.now();
        return dateIssueStart.isBefore(now) && dateIssueEnd.isAfter(now);
    }

    public void checkIssueableCoupon() {
        if( !availableIssueQuantity) {
            throw new CouponIssueException(ErrorCode.INVALID_COUPON_ISSUE_QUANTITY, "모든 발급 수량이 소진되었습니다. couponId: %s".formatted(id));
        }

        if(!availableIssueDate()) {
            throw new CouponIssueException(ErrorCode.INVALID_COUPON_ISSUE_DATE, "발급 가능한 일자가 아닙니다. couponId: %s, issueStart: %s, issueEnd:%s".formatted(id, dateIssueStart, dateIssueEnd));
        }
    }
}