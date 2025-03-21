package com.java.app.enterprise_application.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import({JdbcConfig.class, JpaConfig.class})
public class ApplicationConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("users");
    }



}
