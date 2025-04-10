//package com.kangwon.couponcore.configuration;
//
//import com.github.benmanes.caffeine.cache.Caffeine;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.caffeine.CaffeineCacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import java.time.Duration;
//
//@Configuration
//public class LocalCacheConfiguration {
//    @Bean
//    @Primary
//    public CacheManager localCacheManager() {
//        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
//        caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
//                .expireAfterWrite(Duration.ofSeconds(10))
//
//                .maximumSize(100)
//        );
//        return caffeineCacheManager;
//    }
//}
