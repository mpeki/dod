package dk.pekilidi.dod.items;

import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.ItemDTO;
import dk.pekilidi.dod.items.model.BaseItem;
import dk.pekilidi.dod.items.model.ItemType;
import dk.pekilidi.dod.skill.SkillNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ItemService {

  private static final ModelMapper modelMapper = new ModelMapper();

  private final ItemRepository<BaseItem> repo;

    public ItemService(ItemRepository<BaseItem> repo) {
        this.repo = repo;
    }

    @CacheEvict(value = "items", allEntries = true)
  @Transactional
  public ItemDTO createItem(@NonNull ItemDTO item, Class<? extends BaseItem> type) {
    BaseItem itemEntity = modelMapper.map(item, type);
    itemEntity = repo.save(itemEntity);
    item.setId(itemEntity.getId());
    return item;
  }

  public ItemDTO findItemByKey(String itemKey){
    BaseItem item = repo.findByKey(ItemKey.toItemKey(itemKey));
    if (item == null) {
      throw new ItemNotFoundException("Item for key: " + itemKey + " not found!");
    }
    return modelMapper.map(item, ItemDTO.class);
  }

  public List<ItemDTO> findItemByType(ItemType itemType){
    return Arrays.stream(repo.findByItemType(itemType).toArray()).map(object ->
                          modelMapper.map(object, ItemDTO.class)).toList();
  }

  public List<ItemDTO> findAll() {
    List<BaseItem> result = new ArrayList<>();
    repo.findAll().forEach(result::add);
    return Arrays.stream(result.toArray()).map(object ->
        modelMapper.map(object, ItemDTO.class)).toList();
  }
}
