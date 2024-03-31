package bitlab.redis.service;

import bitlab.redis.annotation.Qcache;
import bitlab.redis.constant.CachePrefix;
import bitlab.redis.entity.Item;
import bitlab.redis.repository.ItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;

  @Qcache(prefix = CachePrefix.ITEM)
  public Item getById(Long id) { // id = 4
    return itemRepository.findById(id).orElse(null);
  }

  @Qcache(prefix = CachePrefix.ITEM)
  public Item save(Item item) {
    return itemRepository.save(item);
  }

  @Qcache(prefix = CachePrefix.ITEM, second = 5)
  public List<Item> getItems() {
    return itemRepository.findAll();
  }

  @CacheEvict(value = CachePrefix.ITEM, key = "#id")
  public void deleteById(Long id) {
    itemRepository.deleteById(id);
  }
}
