package dk.dodgame.domain.items;

import static org.assertj.core.api.Assertions.assertThat;

import dk.dodgame.domain.item.ItemKey;
import dk.dodgame.domain.item.ItemRepository;
import dk.dodgame.domain.item.model.BaseItem;
import dk.dodgame.domain.item.model.Coin;
import dk.dodgame.domain.item.model.ItemType;
import dk.dodgame.domain.item.model.ManyPiece;
import dk.dodgame.domain.item.model.MeleeWeapon;
import dk.dodgame.domain.item.model.ProjectileWeapon;
import dk.dodgame.domain.item.model.ThrowingWeapon;
import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@Tag("regression")
class ItemRepositoryTest {

  @Autowired
  private ItemRepository repository;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  void save_and_find_mithril() {
    ManyPiece mithril = Coin.builder()
        .key(ItemKey.builder().value("mithril").build())
        .price(1000.0)
        .piecesForPrice(1.0)
        .weight(0.01)
        .breakable(false)
        .build();
    BaseItem itemSaved = entityManager.persistFlushFind(mithril);
    BaseItem item = repository.findByKey(ItemKey.builder().value("mithril").build());
    assertThat(item).isInstanceOf(Coin.class);
    assertThat(itemSaved).isEqualTo(item);
  }
  @Test
  void find_gold() {
    BaseItem item = repository.findByKey(ItemKey.builder().value("gold").build());
    assertThat(item).isInstanceOf(Coin.class);
    assertThat(item.getKey()).isEqualTo(ItemKey.builder().value("gold").build());
  }
  @Test
  void find_a_dagger() {
    BaseItem item = repository.findByKey(ItemKey.builder().value("dagger").build());
    assertThat(item).isInstanceOf(MeleeWeapon.class);
    assertThat(item.getKey()).isEqualTo(ItemKey.builder().value("dagger").build());
  }

  @Test
  void find_a_small_bow() {
    BaseItem item = repository.findByKey(ItemKey.toItemKey("small.bow"));
    assertThat(item).isInstanceOf(ProjectileWeapon.class);
    assertThat(item.getKey().getKeyValue()).isEqualTo("small.bow");
  }

  @Test
  void find_a_throwning_spear() {
    BaseItem item = repository.findByKey(ItemKey.toItemKey("throwing.spear"));
    assertThat(item).isInstanceOf(ThrowingWeapon.class);
    assertThat(item.getKey().getKeyValue()).isEqualTo("throwing.spear");
  }

  @Test
  @SuppressWarnings("unchecked")
  void find_all_PROJECTILE_WEAPON() {
    List<? extends BaseItem> projectileWeapons = repository.findByItemType(ItemType.PROJECTILE_WEAPON);
    for (BaseItem projectileWeapon : projectileWeapons) {
      assertThat(projectileWeapon).isInstanceOf(ProjectileWeapon.class);
    }
  }

}