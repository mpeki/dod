package dk.dodgame.domain.item;

import dk.dodgame.data.ItemDTO;
import dk.dodgame.domain.item.model.ArmorPiece;
import dk.dodgame.domain.item.model.ArmorType;
import dk.dodgame.domain.item.model.BaseItem;
import dk.dodgame.domain.item.model.ItemType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ItemService {

  private static final ModelMapper modelMapper = new ModelMapper();

  private final ItemRepository<BaseItem> repo;

  @CacheEvict(value = "items", allEntries = true)
  @Transactional
  public ItemDTO createItem(@NonNull ItemDTO item, Class<? extends BaseItem> type) {
    BaseItem itemEntity = modelMapper.map(item, type);
    itemEntity = repo.save(itemEntity);
    item.setId(itemEntity.getId());
    return item;
  }

  public ItemDTO findItemByKey(String itemKey) {
    BaseItem item = repo.findByKey(ItemKey.toItemKey(itemKey));
    if (item == null) {
      String[] keyAndArmorType = itemKey.split("\\.");
      if(keyAndArmorType.length == 2){
          item = repo.findByKeyAndType(ItemKey.toItemKey(keyAndArmorType[0]), ArmorType.valueOf(keyAndArmorType[1].toUpperCase()));
      } else {
        throw new ItemNotFoundException("Item for key: " + itemKey + " not found!");
      }

    }
    return modelMapper.map(item, ItemDTO.class);
  }

  public List<ItemDTO> findItemByType(ItemType itemType) {
    return Arrays
        .stream(repo.findByItemType(itemType).toArray())
        .map(object -> modelMapper.map(object, ItemDTO.class))
        .toList();
  }

  public List<ItemDTO> findAll() {
//    List<BaseItem> result = new ArrayList<>();
    Iterable<BaseItem> result = repo.findAll();
//    result.forEach(result::add);
    List<BaseItem> itemList = new ArrayList<>();
    result.forEach(itemList::add);
    return Arrays.stream(itemList.toArray()).map(object -> modelMapper.map(object, ItemDTO.class)).toList();
  }
}
