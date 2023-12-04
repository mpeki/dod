package dk.dodgame.domain.items;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dk.dodgame.domain.item.ItemKey;
import dk.dodgame.domain.item.ItemRepository;
import dk.dodgame.domain.item.ItemService;
import dk.dodgame.domain.item.model.HandGrip;
import dk.dodgame.domain.item.model.ItemType;
import dk.dodgame.domain.item.model.MeleeWeapon;
import dk.dodgame.util.character.CharacterMapper;
import dk.dodgame.data.ItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class ItemServiceTest {

  ItemService itemService;
  MeleeWeapon meleeWeaponEntity;
  ItemDTO genericItem;

  private final CharacterMapper modelMapper = new CharacterMapper();

  @BeforeEach
  @SuppressWarnings("unchecked")
  void setUp() {
    meleeWeaponEntity = MeleeWeapon.builder()
        .key(ItemKey.toItemKey("short.sword"))
        .breakable(true)
        .damage("1t6+1")
        .handGrip(HandGrip.ONE_HANDED)
        .itemType(ItemType.MELEE_WEAPON)
        .weight(1.0)
        .bp(10)
        .build();
    genericItem = modelMapper.map(meleeWeaponEntity, ItemDTO.class);
    ItemRepository itemRepo = mock(ItemRepository.class);
    itemService = new ItemService(itemRepo);
    when(itemRepo.save(any(MeleeWeapon.class))).thenReturn(meleeWeaponEntity.toBuilder().id("test-melee-weapon-id").build());
  }

  @Test
  void createItem() {
    ItemDTO result = itemService.createItem(genericItem, MeleeWeapon.class);
    assertEquals("test-melee-weapon-id", result.getId());
  }

  @Test
  @SuppressWarnings("ConstantConditions")
  void createNullItem() {
    assertThrows(NullPointerException.class, () -> itemService.createItem(null, MeleeWeapon.class));
  }

}