package dk.pekilidi.dod.items;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dk.pekilidi.dod.items.model.HandGrip;
import dk.pekilidi.dod.items.model.ItemType;
import dk.pekilidi.dod.items.model.MeleeWeapon;
import dk.pekilidi.dod.util.character.CharacterMapper;
import dk.pekilidi.dod.data.ItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class ItemServiceTest {

  ItemRepository itemRepo;
  ItemService itemService;
  MeleeWeapon meleeWeaponEntity;
  ItemDTO genericItem;

  private final CharacterMapper modelMapper = new CharacterMapper();

  @BeforeEach
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
    itemRepo = mock(ItemRepository.class);
    itemService = new ItemService(itemRepo);
    when(itemRepo.save(any(MeleeWeapon.class))).thenReturn(meleeWeaponEntity.toBuilder().id("test-melee-weapon-id").build());
  }

  @Test
  void createItem() {
    ItemDTO result = itemService.createItem(genericItem, MeleeWeapon.class);
    assertEquals("test-melee-weapon-id", result.getId());
  }

  @Test
  void createNullItem() {
    assertThrows(NullPointerException.class, () -> itemService.createItem(null, MeleeWeapon.class));
  }

}