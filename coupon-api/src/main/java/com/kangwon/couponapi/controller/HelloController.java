package com.kangwon.couponapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() throws InterruptedException {
        Thread.sleep(500);
        return "hello world";
    } // 초당 2건 처리 가능 * 200(서버에서 동시에 처리할 수 있는 수) = 400
}