package bitlab.redis.controller;

import bitlab.redis.cahce.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {
  private final RedisService redisService;

  @PostMapping("/set")
  public void setValue(@RequestParam String key, @RequestParam String value) {
    redisService.setValue(key, value);
  }

  @GetMapping("/get")
  public Object getValue(@RequestParam String key) {
    return redisService.getValue(key);
  }

}
