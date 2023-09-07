package dk.pekilidi.dod.items;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import dk.pekilidi.dod.data.ItemDTO;
import dk.pekilidi.dod.items.model.ItemType;
import dk.pekilidi.dod.items.model.MeleeWeapon;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/items")
public class ItemController {

  ItemService itemService;

  @GetMapping("/key/{key}")
  public ItemDTO getItemByKey(@PathVariable String key) {
    return itemService.findItemByKey(key);
  }

  @GetMapping
  public List<ItemDTO> getAllItems() {
    return itemService.findAll();
  }

  @GetMapping("/type/{itemType}")
  public List<ItemDTO> findByType(@PathVariable ItemType itemType) {
    List<ItemDTO> result = itemService.findItemByType(itemType);
    return result;
  }

  @PostMapping(value = "/melee_weapon", consumes = MediaType.APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  ItemDTO postMeleeWeapon(@RequestBody ItemDTO newMeleeWeapon) {
    return itemService.createItem(newMeleeWeapon, MeleeWeapon.class);
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void itemNotFoundHandler(ItemNotFoundException ex) { /* Just need the HttpStatus.NOT_FOUND */ }
}
