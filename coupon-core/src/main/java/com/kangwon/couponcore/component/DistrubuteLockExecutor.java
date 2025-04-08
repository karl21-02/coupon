//package com.kangwon.couponcore.component;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.TimeUnit;
//
//@RequiredArgsConstructor
//@Component
//@Slf4j
//public class DistrubuteLockExecutor {
//    private final RedissonClient redisson;
//
//    public void execute(String lockName, long waitMilliSecond, long leaseMilliSecond, Runnable runnable) {
//        RLock lock = redisson.getLock("lock");
//        try {
//            // lock을 획득
//            boolean isLocked = lock.tryLock(waitMilliSecond, leaseMilliSecond, TimeUnit.MILLISECONDS);
//            if(!isLocked) {
//                throw new IllegalStateException("[" + lockName + "] lock 획득 실패");
//            }
//            runnable.run();
//        } catch (InterruptedException e) {
//            log.error(e.getMessage(), e);
//            throw new RuntimeException(e);
//        }
//        finally {
//            if(lock.isHeldByCurrentThread()) {
//                lock.unlock();
//            }
//        }
//        runnable.run();
//    }
//}
