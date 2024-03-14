package dk.dodgame.domain.items;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dk.dodgame.DodApplication;
import dk.dodgame.data.ItemDTO;
import dk.dodgame.domain.item.ItemKey;
import dk.dodgame.domain.item.ItemNotFoundException;
import dk.dodgame.domain.item.ItemService;
import dk.dodgame.domain.item.model.ItemType;
import dk.dodgame.domain.item.model.MeleeWeapon;
import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DodApplication.class)
@Tag("regression")
class ItemsServiceIntegrationTest {

  @Autowired
  private ItemService cut;


  @Test
  void find_all_items(){
    int armorCount, coinCount, meleeWeaponCount, containerCount, projectileCount, throwingWeaponCount, projectileWeaponCount, defaultCount = 0;
    armorCount = coinCount = meleeWeaponCount = containerCount = projectileCount = throwingWeaponCount = projectileWeaponCount = defaultCount;
    List<ItemDTO> meleeWeapons = cut.findAll();
    assertTrue(meleeWeapons.size() > 10 );
    for (ItemDTO item : meleeWeapons) {
      switch(item.getItemType()){
        case MELEE_WEAPON -> meleeWeaponCount++;
        case CONTAINER -> containerCount++;
        case PROJECTILE -> projectileCount++;
        case COIN -> coinCount++;
        case THROWING_WEAPON -> throwingWeaponCount++;
        case PROJECTILE_WEAPON -> projectileWeaponCount++;
        case ARMOR -> armorCount++;
        default -> defaultCount++;
      }
    }
    assertTrue(meleeWeaponCount > 1);
    assertTrue(containerCount > 1);
    assertTrue(coinCount > 1);
    assertTrue(projectileCount > 1);
    assertTrue(throwingWeaponCount > 1);
    assertTrue(projectileWeaponCount > 1);
    assertEquals(0, defaultCount);
  }

  @Test
  void find_all_melee_weapons(){
    int meleeWeaponCount = 0;
    List<ItemDTO> meleeWeapons = cut.findItemByType(ItemType.MELEE_WEAPON);
    assertTrue(meleeWeapons.size() > 1 );
  }

  @Test
  void find_melee_weapon_by_key(){
    ItemDTO meleeWeapon = cut.findItemByKey("dagger");
    assertEquals(ItemType.MELEE_WEAPON, meleeWeapon.getItemType());
    assertEquals("dagger", meleeWeapon.getItemKey().getKeyValue());
  }

  @Test
  void findLeatherItem(){
    ItemDTO armor = cut.findItemByKey("leather.helm");
    assertEquals(ItemType.ARMOR, armor.getItemType());
    assertEquals("leather.helm", armor.getItemKey().getKeyValue());
  }


  @Test
  void createANewMeleeWeapon(){
    ItemDTO atomicBomb = ItemDTO.builder()
        .itemKey(ItemKey.toItemKey("atomic.bomb"))
        .damage("1t100+9999999999999")
        .weight(20.0)
        .build();

    atomicBomb = cut.createItem(atomicBomb, MeleeWeapon.class);
    assertNotNull(atomicBomb.getId());
    assertTrue(atomicBomb.isBreakable());
  }

  @Test
  void findNonExistingItem(){
    assertThrows(ItemNotFoundException.class, () -> cut.findItemByKey("item.that.does.not.exist"));
  }
}
