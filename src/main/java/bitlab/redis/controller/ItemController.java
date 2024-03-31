package bitlab.redis.controller;

import bitlab.redis.entity.Item;
import bitlab.redis.cahce.RedisService;
import bitlab.redis.service.ItemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {
  @Autowired
  private ItemService itemService;

  @PostMapping
  public Item create(@RequestBody Item item) {
    return itemService.save(item);
  }

  @GetMapping("{id}")
  public Item getItemById(@PathVariable Long id) {
    return itemService.getById(id);
  }

  @GetMapping()
  public List<Item> getItems() {
    return itemService.getItems();
  }

  @PutMapping
  public Item update(@RequestBody Item item) {
    return itemService.save(item);
  }

  @DeleteMapping("{id}")
  public void deleteById(@PathVariable Long id) {
    itemService.deleteById(id);
  }
}
