package com.kangwon.couponapi.controller;

import com.kangwon.couponapi.controller.dto.CouponIssueResponseDto;
import com.kangwon.couponcore.exception.CouponIssueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CouponControllerAdvice {

    @ExceptionHandler(CouponIssueException.class)
    public CouponIssueResponseDto couponIssueExceptionHandler(CouponIssueException e) {
        return new CouponIssueResponseDto(false, e.getErrorCode().message);
    }
}
