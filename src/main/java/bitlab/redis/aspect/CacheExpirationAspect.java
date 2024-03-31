package bitlab.redis.aspect;

import bitlab.redis.annotation.Qcache;
import bitlab.redis.cahce.RedisService;
import bitlab.redis.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Arrays;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Aspect
@Component
@RequiredArgsConstructor
public class CacheExpirationAspect {

  private final RedisService redisService;
  private final JsonUtils jsonUtils;
  @Value("${default.cache.duration.sec}")
  private Long defaultDuration;

  @Around("@annotation(bitlab.redis.annotation.Qcache)")
  public Object cache(ProceedingJoinPoint joinPoint) throws Throwable {
    final var id = extractId(joinPoint);
    final var prefix = extractAnnotation(joinPoint).prefix();
    final var second = extractSeconds(joinPoint);

    // Генерация ключа кэша на основе префикса и идентификатора.
    final var key = generateKey(prefix, id);

    // Проверяем, есть ли данные в кэше.
    var value = redisService.getValue(key);
    if (Objects.isNull(value)) {
      // Если данных нет в кэше, выполнение метода и сохранение результата в кэш.
      value = joinPoint.proceed();
      redisService.setValue(key, value);
      redisService.setExpireSeconds(key, second);
    }
    return value;
  }

  private long extractSeconds(ProceedingJoinPoint joinPoint) {
    long seconds = extractAnnotation(joinPoint).second();
    return -1 == seconds ? defaultDuration : seconds;
  }

  private String extractId(ProceedingJoinPoint joinPoint) throws JsonProcessingException {
    var value = Arrays.stream(joinPoint.getArgs()).findFirst().orElse(null);
    if (Objects.isNull(value)) {
      return null;
    }
    if (value instanceof Long) {
      return String.valueOf(value);
    }
    return String.valueOf(jsonUtils.fromObject(value));
  }

  /**
   * Метод для извлечения аннотации Qcache.
   */
  private Qcache extractAnnotation(ProceedingJoinPoint joinPoint) {
    // Получение аннотации Qcache из метода
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    return signature.getMethod().getAnnotation(Qcache.class);
  }

  /**
   * Метод для генерации ключа.
   *
   * @param prefix - префикс.
   * @param id     - идентификатор объекта.
   * @return - ключ.
   */
  private String generateKey(String prefix, String id) {
    if (!StringUtils.hasText(id)) {
      return prefix;
    }
    return String.join("::", prefix, id);
  }
}
