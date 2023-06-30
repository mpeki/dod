package dk.pekilidi.dod.items;

import dk.pekilidi.dod.items.model.BaseItem;
import dk.pekilidi.dod.items.model.ItemType;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository<T extends BaseItem> extends CrudRepository<T, String> {

  <S extends BaseItem> List<S> findByItemType(ItemType itemType);
  BaseItem findByKey(ItemKey key);
}
