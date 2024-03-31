package bitlab.redis.constant;

import bitlab.redis.entity.Item;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CachePrefix {

  public static final String ITEM = "items";
}
