package dk.pekilidi.dod.character.data;

import static dk.pekilidi.dod.items.model.Coin.COPPER_PIECE;
import static dk.pekilidi.dod.items.model.Coin.GOLD_PIECE;
import static dk.pekilidi.dod.items.model.Coin.SILVER_PIECE;
import static dk.pekilidi.dod.items.model.ItemType.COIN;
import static dk.pekilidi.dod.items.model.ItemType.MELEE_WEAPON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.character.model.body.BodyPartName;
import dk.pekilidi.dod.data.BaseTraitDTO;
import dk.pekilidi.dod.data.BodyPartDTO;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.CharacterItemDTO;
import dk.pekilidi.dod.data.CharacterSkillDTO;
import dk.pekilidi.dod.data.ItemDTO;
import dk.pekilidi.dod.data.SkillDTO;
import dk.pekilidi.dod.items.InsufficientFundsException;
import dk.pekilidi.dod.items.ItemKey;
import dk.pekilidi.dod.items.model.Projectile;
import dk.pekilidi.dod.skill.SkillKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class CharacterDTOTest {

  CharacterDTO characterDTO;

  @BeforeEach
  void setUp() {
    characterDTO = new CharacterDTO();
  }

  @Test
  void requiredArgsConstructor() {
    characterDTO = CharacterDTO.builder().name("testName").build();
    assertEquals("testName", characterDTO.getName());
  }

  @Test
  void addBaseTrait() {
    assertEquals(0, characterDTO.getBaseTraits().size());
    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 5);
    characterDTO.decrementTrait(BaseTraitName.DEXTERITY, 5);
    characterDTO.updateBaseTrait(BaseTraitName.DEXTERITY, 7);
    characterDTO.addBaseTrait(new BaseTraitDTO(BaseTraitName.DEXTERITY, 0, 0));
    assertNotNull(characterDTO.getBaseTraits());
  }

  @Test
  void incrementTrait() {
    assertEquals(0, characterDTO.getBaseTraits().size());
    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 5);
    characterDTO.addBaseTrait(new BaseTraitDTO(BaseTraitName.DEXTERITY, 5, 5));
    assertNotNull(characterDTO.getBaseTraits());
    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 5);
    assertEquals(10, characterDTO.getBaseTraits().get(BaseTraitName.DEXTERITY).getCurrentValue());
    characterDTO.incrementTrait(BaseTraitName.SIZE, 5);
    assertNull(characterDTO.getBaseTraits().get(BaseTraitName.SIZE));
  }

  @Test
  void decrementTrait() {
    assertEquals(0, characterDTO.getBaseTraits().size());
    characterDTO.decrementTrait(BaseTraitName.DEXTERITY, 5);
    assertEquals(0, characterDTO.getBaseTraits().size());
    characterDTO.addBaseTrait(new BaseTraitDTO(BaseTraitName.DEXTERITY, 5, 5));
    assertNotNull(characterDTO.getBaseTraits());
    assertEquals(1, characterDTO.getBaseTraits().size());
    characterDTO.decrementTrait(BaseTraitName.DEXTERITY, 5);
    assertEquals(0, characterDTO.getBaseTraits().get(BaseTraitName.DEXTERITY).getCurrentValue());
    characterDTO.decrementTrait(BaseTraitName.SIZE, 5);
    assertNull(characterDTO.getBaseTraits().get(BaseTraitName.SIZE));
  }

  @Test
  void updateBaseTrait() {
    assertEquals(0, characterDTO.getBaseTraits().size());
    characterDTO.updateBaseTrait(BaseTraitName.DEXTERITY, 7);
    characterDTO.addBaseTrait(new BaseTraitDTO(BaseTraitName.DEXTERITY, 5, 5));
    assertNotNull(characterDTO.getBaseTraits());
    characterDTO.updateBaseTrait(BaseTraitName.DEXTERITY, 7);
    assertEquals(7, characterDTO.getBaseTraits().get(BaseTraitName.DEXTERITY).getCurrentValue());
    characterDTO.updateBaseTrait(BaseTraitName.SIZE, 5);
    assertNull(characterDTO.getBaseTraits().get(BaseTraitName.SIZE));
  }

  @Test
  void groupValues() {
    assertEquals(0, characterDTO.getBaseTraits().size());
    assertEquals(-1, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));
    characterDTO.addBaseTrait(new BaseTraitDTO(BaseTraitName.DEXTERITY, 5, 5));
    assertEquals(1, characterDTO.getBaseTraits().size());
    assertEquals(1, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));
    characterDTO.decrementTrait(BaseTraitName.DEXTERITY, 2);
    assertEquals(3, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(0, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 1);
    assertEquals(4, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(1, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 4);
    assertEquals(8, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(1, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 1);
    assertEquals(9, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(2, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 3);
    assertEquals(12, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(2, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 1);
    assertEquals(13, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(3, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 3);
    assertEquals(16, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(3, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 1);
    assertEquals(17, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(4, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 3);
    assertEquals(20, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(4, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 1);
    assertEquals(21, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(5, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 4);
    assertEquals(25, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(5, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 1);
    assertEquals(26, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(6, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 4);
    assertEquals(30, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(6, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 1);
    assertEquals(31, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(7, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 1);
    assertEquals(32, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(7, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 3);
    assertEquals(35, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(7, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 5);
    assertEquals(40, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(7, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 1);
    assertEquals(41, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(8, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 9);
    assertEquals(50, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(8, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 1);
    assertEquals(51, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(9, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 10);
    assertEquals(61, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(10, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 50);
    assertEquals(111, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(15, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 5);
    assertEquals(116, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(15, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 4);
    assertEquals(120, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(15, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 1);
    assertEquals(121, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(16, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));
  }

  @Test
  void testFetchingNonExistentTrait() {
    assertEquals(-1, characterDTO.getBaseTraitValue(null));
    assertEquals(-1, characterDTO.getBaseTraitGroupValue(null));
  }

  @Test
  void testAddingAndRemovingASkill() {
    //Test adding a null skill - a NullPointer should be thrown and no skill should be added
    assertEquals(0, characterDTO.getSkills().size());
    assertThrows(NullPointerException.class, () -> characterDTO.addSkill(null));
    assertEquals(0, characterDTO.getSkills().size());

    //Test adding a non skill - a NullPointer should be thrown and no skill should be added
    assertThrows(NullPointerException.class, () -> characterDTO.addSkill(CharacterSkillDTO.builder().build()));
    assertEquals(0, characterDTO.getSkills().size());

    //Test adding a skill - the skill should be added
    CharacterSkillDTO acrobatics = CharacterSkillDTO
        .builder()
        .skillName("acrobatics")
        .skill(SkillDTO.builder().key(SkillKey.toSkillKey("acrobatics")).build())
        .build();
    characterDTO.addSkill(acrobatics);
    assertEquals(1, characterDTO.getSkills().size());
    assertTrue(characterDTO.getSkills().containsValue(acrobatics));
    characterDTO.removeSkill(acrobatics.getSkill().getKey().getKeyValue());
    assertEquals(0, characterDTO.getSkills().size());

  }
  @Test
  void testUpdatingFV(){
    CharacterSkillDTO fst = CharacterSkillDTO
        .builder()
        .skillName("fst")
        .skill(SkillDTO.builder().key(SkillKey.toSkillKey("fst")).build())
        .build();
    characterDTO.addSkill(fst);
    characterDTO.updateFv(fst.getSkillName(), 5);
    assertEquals(5, characterDTO.getSkill(fst.getSkillName()).getFv());
  }

  @Test
  void testAddingABodyPart() {
    //Test adding a null body part - a NullPointer should be thrown and no body part should be added
    assertEquals(0, characterDTO.getBodyParts().size());
    assertThrows(NullPointerException.class, () -> characterDTO.addBodyPart(null));
    assertEquals(0, characterDTO.getBodyParts().size());

    //Test adding an non body part - a NullPointer should be thrown and no body part should be added
    assertThrows(NullPointerException.class, () -> characterDTO.addBodyPart(BodyPartDTO.builder().build()));
    assertEquals(0, characterDTO.getBodyParts().size());

    //Test adding a body part - the body part should be added
    BodyPartDTO head = BodyPartDTO
        .builder()
        .name(BodyPartName.HEAD)
        .build();
    characterDTO.addBodyPart(head);
    assertEquals(1, characterDTO.getBodyParts().size());
    assertTrue(characterDTO.getBodyParts().containsValue(head));
  }

  @Test
  void testAddItem() {

    //Test adding a null item - a NullPointer should be thrown and no item should be added
    assertEquals(0, characterDTO.getItems().size());
    assertThrows(NullPointerException.class, () -> characterDTO.addItem(null));
    assertEquals(0, characterDTO.getItems().size());

    //Test adding an non item - a NullPointer should be thrown and no item should be added
    assertThrows(NullPointerException.class, () -> characterDTO.addItem(CharacterItemDTO.builder().build()));
    assertEquals(0, characterDTO.getItems().size());

    //Test adding an item - the item should be added
    CharacterItemDTO shortSword = CharacterItemDTO
        .builder()
        .item(ItemDTO.builder().itemKey(ItemKey.toItemKey("short.sword")).itemType(MELEE_WEAPON).build())
        .build();
    characterDTO.addItem(shortSword);
    assertEquals(1, characterDTO.getItems().size());
    assertTrue(characterDTO.getItems().containsValue(shortSword));
    //Test adding the same item again - the item (object) should also not be added.
    characterDTO.addItem(shortSword);
    assertEquals(1, characterDTO.getItems().size());
    //Create a new short.sword and add it - the item should be added.
    CharacterItemDTO shortSword2 = CharacterItemDTO
        .builder()
        .item(ItemDTO.builder().itemKey(ItemKey.toItemKey("short.sword")).itemType(MELEE_WEAPON).build())
        .build();
    characterDTO.addItem(shortSword2);
    assertEquals(2, characterDTO.getItems().size());

  }

  @Test
  void testRemoveItem() {
    //Test adding an item - the item should be added
    CharacterItemDTO shortSword = CharacterItemDTO
        .builder()
        .item(ItemDTO.builder().itemKey(ItemKey.toItemKey("short.sword")).itemType(MELEE_WEAPON).build())
        .build();
    characterDTO.addItem(shortSword);
    assertEquals(1, characterDTO.getItems().size());
    assertTrue(characterDTO.getItems().containsValue(shortSword));
    //Test removing the item - the item should be removed
    characterDTO.removeItem(shortSword);
    assertEquals(0, characterDTO.getItems().size());
  }


  @Test
  void testAddAndRemoveMultiItem() {
    //Test adding an item - the item should be added
    CharacterItemDTO silver = CharacterItemDTO
        .builder()
        .item(ItemDTO.builder().itemKey(ItemKey.toItemKey("silver")).itemType(COIN).build())
        .itemName("silver")
        .quantity(20)
        .build();
    CharacterItemDTO silverToBeRemoved = CharacterItemDTO
        .builder()
        .item(ItemDTO.builder().itemKey(ItemKey.toItemKey("silver")).itemType(COIN).build())
        .quantity(15)
        .build();
    characterDTO.addItem(silver);
    assertEquals(1, characterDTO.getItems().size());
    assertTrue(characterDTO.getItems().containsValue(silver));
    characterDTO.addItem(silver);
    //Test removing the item - the item should be removed
    characterDTO.removeItem(silverToBeRemoved);
    assertEquals(25, characterDTO.getItems().get(silver.getItemName()).getQuantity());
  }


  @Test
  void testAddAndSubtractCoins(){
    assertEquals(0, characterDTO.getAmountOf(COPPER_PIECE));
    assertThrows(InsufficientFundsException.class, () -> characterDTO.subtractCoins(1, COPPER_PIECE));
    //Let's add some silver coins
    characterDTO.addCoins(100, SILVER_PIECE);
    assertEquals(100, characterDTO.getAmountOf(SILVER_PIECE));
    //Now let's add some more silver coins
    assertEquals(150, characterDTO.addCoins(50, SILVER_PIECE));
    //Now let's add some gold coins
    characterDTO.addCoins(10, GOLD_PIECE);
    assertEquals(10, characterDTO.getAmountOf(GOLD_PIECE));
    //Now let's buy something with gold coins
    assertEquals(5, characterDTO.subtractCoins(5, GOLD_PIECE));
    //Now let's buy something with silver coins
    characterDTO.subtractCoins(75, SILVER_PIECE);
    assertEquals(75, characterDTO.getAmountOf(SILVER_PIECE));
    //Now let's buy something with silver coins - using too much silver coins should throw an exception
    assertThrows(InsufficientFundsException.class, () -> characterDTO.subtractCoins(100, SILVER_PIECE));
    //Let's try to buy something with copper coins - this should throw an exception - since we have none.
    assertThrows(InsufficientFundsException.class, () -> characterDTO.subtractCoins(100, SILVER_PIECE));
  }

  @Test
  void testGettingNumberOfManyItems(){
    characterDTO.addManyPieceItem(20, Projectile.ARROW);
    assertEquals(1, characterDTO.getItems().size());
    characterDTO.addManyPieceItem(5, Projectile.ARROW);
    assertEquals(1, characterDTO.getItems().size());
    assertEquals(25, characterDTO.getNumberOf(Projectile.ARROW));
  }

  @Test
  void testGettingNullType(){
    assertEquals(0, characterDTO.getNumberOf(null));
  }

  @Test
  void testGettingNotInInventoryType(){
    assertEquals(0, characterDTO.getNumberOf(Projectile.ARROW));
  }

}
