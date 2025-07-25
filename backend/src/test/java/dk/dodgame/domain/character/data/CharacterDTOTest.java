package dk.dodgame.domain.character.data;

import static dk.dodgame.domain.item.model.ItemType.COIN;
import static dk.dodgame.domain.item.model.ItemType.MELEE_WEAPON;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dk.dodgame.data.*;
import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.character.model.CharacterState;
import dk.dodgame.domain.character.model.body.BodyPartName;
import dk.dodgame.domain.item.InsufficientFundsException;
import dk.dodgame.domain.item.ItemKey;
import dk.dodgame.domain.item.model.Coin;
import dk.dodgame.domain.item.model.Projectile;
import dk.dodgame.domain.skill.SkillKey;
import dk.dodgame.util.rules.RulesUtil;

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
    assertEquals(0, characterDTO.getAmountOf(Coin.COPPER_PIECE));
    assertThrows(InsufficientFundsException.class, () -> characterDTO.subtractCoins(1, Coin.COPPER_PIECE));
    //Let's add some silver coins
    characterDTO.addCoins(100, Coin.SILVER_PIECE);
    assertEquals(100, characterDTO.getAmountOf(Coin.SILVER_PIECE));
    //Now let's add some more silver coins
    assertEquals(150, characterDTO.addCoins(50, Coin.SILVER_PIECE));
    //Now let's add some gold coins
    characterDTO.addCoins(10, Coin.GOLD_PIECE);
    assertEquals(10, characterDTO.getAmountOf(Coin.GOLD_PIECE));
    //Now let's buy something with gold coins
    assertEquals(5, characterDTO.subtractCoins(5, Coin.GOLD_PIECE));
    //Now let's buy something with silver coins
    characterDTO.subtractCoins(75, Coin.SILVER_PIECE);
    assertEquals(75, characterDTO.getAmountOf(Coin.SILVER_PIECE));
    //Now let's buy something with silver coins - using too much silver coins should throw an exception
    assertThrows(InsufficientFundsException.class, () -> characterDTO.subtractCoins(100, Coin.SILVER_PIECE));
    //Let's try to buy something with copper coins - this should throw an exception - since we have none.
    assertThrows(InsufficientFundsException.class, () -> characterDTO.subtractCoins(100, Coin.SILVER_PIECE));
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

	/* ------------------------------------------------------------------
	 *  Base-trait accessors
	 * ------------------------------------------------------------------ */

	@Test
	void getBaseTraitStartValue_ReturnsCorrectValue() {
		assertEquals(-1,
				characterDTO.getBaseTraitStartValue(BaseTraitName.STRENGTH),
				"Unknown trait should yield –1");

		characterDTO.addBaseTrait(new BaseTraitDTO(BaseTraitName.STRENGTH, 12, 12));

		assertEquals(12,
				characterDTO.getBaseTraitStartValue(BaseTraitName.STRENGTH),
				"Start value must match the inserted DTO");
	}

	/* ------------------------------------------------------------------
	 *  Skill retrieval
	 * ------------------------------------------------------------------ */

	@Test
	void getSkill_ReturnsAddedSkill() {
		CharacterSkillDTO skillDTO =
				CharacterSkillDTO.builder()
						.skill(SkillDTO.builder()
								.key(new SkillKey("lock-picking"))
								.build())
						.fv(25)
						.build();

		assertNull(characterDTO.getSkill("lock-picking"),
				"Skill map initially empty – expect null");

		characterDTO.addSkill(skillDTO);

		assertEquals(skillDTO,
				characterDTO.getSkill("lock-picking"),
				"Should return the same object that was added");

		characterDTO.updateFv("lock-picking", 35);
		assertEquals(35, characterDTO.getSkill("lock-picking").getFv(),
				"FV update must be reflected in the stored DTO");
	}

	/* ------------------------------------------------------------------
	 *  Coin handling
	 * ------------------------------------------------------------------ */

	@Test
	void coinOperations_AddAndSubtract() throws InsufficientFundsException {
		assertEquals(0, characterDTO.getAmountOf(Coin.COPPER_PIECE));

		characterDTO.addCoins(137, Coin.COPPER_PIECE);
		assertEquals(137, characterDTO.getAmountOf(Coin.COPPER_PIECE));

		int remaining = characterDTO.subtractCoins(37, Coin.COPPER_PIECE);
		assertEquals(100, remaining);
		assertEquals(100, characterDTO.getAmountOf(Coin.COPPER_PIECE));
	}

	@Test
	void subtractCoins_WithInsufficientFunds_Throws() {
		assertThrows(InsufficientFundsException.class,
				() -> characterDTO.subtractCoins(1, Coin.GOLD_PIECE));
	}

	/* ------------------------------------------------------------------
	 *  Many-piece items
	 * ------------------------------------------------------------------ */

	@Test
	void addManyPieceItem_PutsItemIntoInventory() {
		CharacterItemDTO item = characterDTO.addManyPieceItem(4, Projectile.ARROW);

		assertNotNull(item);
		assertEquals("arrow", item.getItemName());
		assertEquals(4, item.getQuantity());

		List<CharacterItemDTO> arrows =
				characterDTO.getItems().values().stream()
						.filter(i -> i.getItemName().equals("arrow"))
						.toList();

		assertEquals(1, arrows.size(), "Exactly one ARROW entry expected");
		assertEquals(4, arrows.getFirst().getQuantity());
	}

	/* ---------------------------------------------------------------
	 *  Tests for getItemsIn()
	 * --------------------------------------------------------------- */
	@Nested
	class GetItemsInTests {

		@Test
		void returnsDefaultNoItemWhenNothingHasBeenPlaced() {
			CharacterDTO dto = new CharacterDTO();

			List<CharacterItemDTO> items = dto.getItemsIn(BodyPartName.RIGHT_ARM);

			assertEquals(1, items.size(), "RIGHT_ARM list should contain the default entry");
			assertEquals(CharacterDTO.NO_ITEM, items.getFirst().getItemName());
		}

		@Test
		void returnsEmptyListWhenBodyPartKeyIsMissing() {
			CharacterDTO dto = new CharacterDTO();
			// Remove the entry completely to simulate “unknown body-part”.
			dto.getWield().remove(BodyPartName.HEAD);

			List<CharacterItemDTO> items = dto.getItemsIn(BodyPartName.HEAD);

			assertTrue(items.isEmpty(), "Unknown body-part should yield an empty list");
		}

		@Test
		void returnsCustomItemsWhenTheyArePresent() {
			CharacterDTO dto = new CharacterDTO();

			CharacterItemDTO sword = CharacterItemDTO.builder().itemName("Short-Sword").build();
			CharacterItemDTO torch = CharacterItemDTO.builder().itemName("Torch").build();

			// Replace the default immutable List with a mutable one that contains our items.
			dto.getWield().put(
					BodyPartName.RIGHT_ARM,
					new ArrayList<>(List.of(sword, torch))
			);

			List<CharacterItemDTO> items = dto.getItemsIn(BodyPartName.RIGHT_ARM);

			assertEquals(2, items.size());
			assertTrue(items.contains(sword));
			assertTrue(items.contains(torch));
		}
	}

	/* ---------------------------------------------------------------
	 *  Tests for applyDamage()
	 * --------------------------------------------------------------- */
	@Nested
	class ApplyDamageTests {

		private CharacterDTO buildCharacter(int hp, int constitution) {
			CharacterDTO dto = new CharacterDTO();

			// TOTAL body-part hit-points
			BodyPartDTO total =
					BodyPartDTO.builder()
							.name(BodyPartName.TOTAL)
							.maxHP(hp)
							.currentHP(hp)
							.build();
			dto.addBodyPart(total);

			// Constitution base-trait
			BaseTraitDTO con =
					BaseTraitDTO.builder()
							.traitName(BaseTraitName.CONSTITUTION)
							.startValue(constitution)
							.currentValue(constitution)
							.groupValue(RulesUtil.calculateGroupValue(constitution))
							.build();
			dto.addBaseTrait(con);

			dto.setState(CharacterState.IN_PLAY);
			return dto;
		}

		@Test
		void hpReducesButStateRemainsWhenDamageIsNonLethal() {
			CharacterDTO dto = buildCharacter(10, 5);

			dto.applyDamage(3);

			assertEquals(7, dto.getBodyParts().get(BodyPartName.TOTAL).getCurrentHP());
			assertEquals(CharacterState.IN_PLAY, dto.getState());
		}

		@Test
		void reachingZeroHpSetsStateToIncapacitated() {
			CharacterDTO dto = buildCharacter(10, 5);

			dto.applyDamage(10);

			assertEquals(0, dto.getBodyParts().get(BodyPartName.TOTAL).getCurrentHP());
			assertEquals(CharacterState.INCAPACITATED, dto.getState());
		}

		@Test
		void negativeHpAboveMinusConstitutionKeepsIncapacitatedState() {
			CharacterDTO dto = buildCharacter(10, 5);

			dto.applyDamage(12); // HP becomes −2 (-2 > −5)

			assertEquals(-2, dto.getBodyParts().get(BodyPartName.TOTAL).getCurrentHP());
			assertEquals(CharacterState.INCAPACITATED, dto.getState());
		}

		@Test
		void hpAtOrBelowMinusConstitutionSetsStateToDead() {
			CharacterDTO dto = buildCharacter(10, 5);

			dto.applyDamage(20); // HP becomes −10 (-10 ≤ −5) → DEAD

			assertEquals(-10, dto.getBodyParts().get(BodyPartName.TOTAL).getCurrentHP());
			assertEquals(CharacterState.DEAD, dto.getState());
		}
	}

}
