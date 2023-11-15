package dk.dodgame.domain.item;

import dk.dodgame.domain.item.model.BaseItem;
import dk.dodgame.domain.item.model.ItemType;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository<T extends BaseItem> extends CrudRepository<T, String> {

  <S extends BaseItem> List<S> findByItemType(ItemType itemType);
  BaseItem findByKey(ItemKey key);
}
