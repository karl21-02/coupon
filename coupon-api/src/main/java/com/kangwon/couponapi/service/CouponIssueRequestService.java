package com.kangwon.couponapi.service;

import com.kangwon.couponapi.controller.dto.CouponIssueRequestDto;
//import com.kangwon.couponcore.component.DistrubuteLockExecutor;
import com.kangwon.couponcore.service.AsyncCouponIssueServiceV1;
import com.kangwon.couponcore.service.CouponIssueService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponIssueRequestService {

    private final CouponIssueService couponIssueService;
//    private final DistrubuteLockExecutor distrubuteLockExecutor;
    private final AsyncCouponIssueServiceV1 asyncCouponIssueServiceV1;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    //    public void issueRequestV1(CouponIssueRequestDto requestDto) {
    //        distrubuteLockExecutor.execute("lock_" + requestDto.couponId(), 10000, 10000, () -> {
    //            couponIssueService.issue(requestDto.couponId(), requestDto.userId());
    //        });
    //        log.info("쿠폰 발급 완료. couponId: %s, userId: %s".formatted(requestDto.couponId(), requestDto.userId()));
    //    }
    public void asyncIssueRequestV1(CouponIssueRequestDto requestDto) {
        asyncCouponIssueServiceV1.issue(requestDto.couponId(), requestDto.userId());
    }
}