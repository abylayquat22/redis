package bitlab.redis.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * Для дефолтного времени кэширования
 */
@Configuration
public class CacheConfig {
  @Value("${default.cache.duration.sec}")
  private Long seconds;

  @Bean
  public CacheManager cacheManager(RedisConnectionFactory factory) {
    RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofSeconds(seconds));

    return RedisCacheManager.builder(factory)
        .cacheDefaults(cacheConfiguration)
        .build();
  }
}
