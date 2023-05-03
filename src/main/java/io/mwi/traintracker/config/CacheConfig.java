package io.mwi.traintracker.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

import static java.time.Duration.ofDays;
import static java.time.Duration.ofMinutes;

@Configuration
@EnableCaching
public class CacheConfig {

    public final static String COMPOSITION_CACHE = "compositions";
    public final static String STATIONS_CACHE = "stations";

    @Bean
    public CacheManager cacheManager() {
        var manager = new SimpleCacheManager();
        manager.setCaches(List.of(
                buildCache(COMPOSITION_CACHE, ofMinutes(10)),
                buildCache(STATIONS_CACHE, ofDays(1))
        ));
        return manager;
    }

    private CaffeineCache buildCache(String name, Duration ttl) {
        return new CaffeineCache(name, Caffeine.newBuilder()
                .expireAfterWrite(ttl)
                .build());
    }
}
