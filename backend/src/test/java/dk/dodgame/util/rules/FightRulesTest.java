package dk.dodgame.util.rules;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import dk.dodgame.data.CharacterDTO;
import dk.dodgame.data.CharacterItemDTO;
import dk.dodgame.data.CharacterSkillDTO;
import dk.dodgame.data.ItemDTO;
import dk.dodgame.domain.action.model.ActionResult;
import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.character.model.FavoriteHand;
import dk.dodgame.domain.character.model.body.BodyPartName;
import dk.dodgame.domain.item.model.ItemType;
import dk.dodgame.util.Dice;

@Tag("regression")
class FightRulesTest {

	/* -------------------------------------------------------------
	 * getItemFV
	 * ------------------------------------------------------------- */
	@Test
	void getItemFV_ReturnsSkillValueWhenSkillPresent() {
		CharacterDTO character = mock(CharacterDTO.class);
		CharacterItemDTO weapon = mock(CharacterItemDTO.class);
		CharacterSkillDTO skill = mock(CharacterSkillDTO.class);

		when(weapon.getItemName()).thenReturn("Sword");
		when(character.getSkill("Sword")).thenReturn(skill);
		when(skill.getFv()).thenReturn(42);

		assertThat(FightRules.getItemFV(weapon, character)).isEqualTo(42);
	}

	@Test
	void getItemFV_FallsBackToDexterityGroupWhenNoSkill() {
		CharacterDTO character = mock(CharacterDTO.class);
		CharacterItemDTO weapon = mock(CharacterItemDTO.class);

		when(weapon.getItemName()).thenReturn("Sword");
		when(character.getSkill("Sword")).thenReturn(null);
		when(character.getBaseTraitGroupValue(BaseTraitName.DEXTERITY)).thenReturn(3);

		assertThat(FightRules.getItemFV(weapon, character)).isEqualTo(3);
	}

	/* -------------------------------------------------------------
	 * hasHighestBaseTrait
	 * ------------------------------------------------------------- */
	@Test
	void hasHighestBaseTrait_FirstOpponentWins() {
		CharacterDTO a = mock(CharacterDTO.class);
		CharacterDTO b = mock(CharacterDTO.class);
		when(a.getBaseTraitValue(BaseTraitName.DEXTERITY)).thenReturn(6);
		when(b.getBaseTraitValue(BaseTraitName.DEXTERITY)).thenReturn(4);

		assertThat(FightRules.hasHighestBaseTrait(a, b, BaseTraitName.DEXTERITY)).isEqualTo(a);
	}

	@Test
	void hasHighestBaseTrait_SecondOpponentWins() {
		CharacterDTO a = mock(CharacterDTO.class);
		CharacterDTO b = mock(CharacterDTO.class);
		when(a.getBaseTraitValue(BaseTraitName.DEXTERITY)).thenReturn(2);
		when(b.getBaseTraitValue(BaseTraitName.DEXTERITY)).thenReturn(5);

		assertThat(FightRules.hasHighestBaseTrait(a, b, BaseTraitName.DEXTERITY)).isEqualTo(b);
	}

	@Test
	void hasHighestBaseTrait_TieReturnsNull() {
		CharacterDTO a = mock(CharacterDTO.class);
		CharacterDTO b = mock(CharacterDTO.class);
		when(a.getBaseTraitValue(BaseTraitName.DEXTERITY)).thenReturn(4);
		when(b.getBaseTraitValue(BaseTraitName.DEXTERITY)).thenReturn(4);

		assertThat(FightRules.hasHighestBaseTrait(a, b, BaseTraitName.DEXTERITY)).isNull();
	}

	/* -------------------------------------------------------------
	 * hasLongestPrimaryWeapon
	 * ------------------------------------------------------------- */
	@Test
	void hasLongestPrimaryWeapon_ReturnsCorrectWinner() {
		CharacterDTO a = mock(CharacterDTO.class);
		CharacterDTO b = mock(CharacterDTO.class);

		try (MockedStatic<FightRules> mocked = mockStatic(FightRules.class, CALLS_REAL_METHODS)) {
			mocked.when(() -> FightRules.getPrimaryWeaponLength(a)).thenReturn(120);
			mocked.when(() -> FightRules.getPrimaryWeaponLength(b)).thenReturn(80);

			assertThat(FightRules.hasLongestPrimaryWeapon(a, b)).isEqualTo(a);
		}
	}

	@Test
	void hasLongestPrimaryWeapon_TieReturnsNull() {
		CharacterDTO a = mock(CharacterDTO.class);
		CharacterDTO b = mock(CharacterDTO.class);

		try (MockedStatic<FightRules> mocked = mockStatic(FightRules.class, CALLS_REAL_METHODS)) {
			mocked.when(() -> FightRules.getPrimaryWeaponLength(a)).thenReturn(100);
			mocked.when(() -> FightRules.getPrimaryWeaponLength(b)).thenReturn(100);

			assertThat(FightRules.hasLongestPrimaryWeapon(a, b)).isNull();
		}
	}

	/* -------------------------------------------------------------
	 * getRandomOpponent
	 * ------------------------------------------------------------- */
	@Test
	void getRandomOpponent_ReturnsFirstWhenDiceIsOne() {
		CharacterDTO a = mock(CharacterDTO.class);
		CharacterDTO b = mock(CharacterDTO.class);

		try (MockedStatic<Dice> diceMock = mockStatic(Dice.class)) {
			diceMock.when(() -> Dice.roll("1t2")).thenReturn(1);
			assertThat(FightRules.getRandomOpponent(a, b)).isEqualTo(a);
		}
	}

	@Test
	void getRandomOpponent_ReturnsSecondWhenDiceIsTwo() {
		CharacterDTO a = mock(CharacterDTO.class);
		CharacterDTO b = mock(CharacterDTO.class);

		try (MockedStatic<Dice> diceMock = mockStatic(Dice.class)) {
			diceMock.when(() -> Dice.roll("1t2")).thenReturn(2);
			assertThat(FightRules.getRandomOpponent(a, b)).isEqualTo(b);
		}
	}

	/* -------------------------------------------------------------
	 * calculateDamage
	 * ------------------------------------------------------------- */
	@Test
	void calculateDamage_AddsWeaponDamageAndBonus() {
		CharacterDTO attacker = mock(CharacterDTO.class);
		CharacterItemDTO weaponHolder = mock(CharacterItemDTO.class);
		ItemDTO weaponItem = mock(ItemDTO.class);

		when(weaponHolder.getItem()).thenReturn(weaponItem);
		when(weaponItem.getDamage()).thenReturn("1t10");
		when(attacker.getDamageBonus()).thenReturn("1t4");

		try (MockedStatic<Dice> diceMock = mockStatic(Dice.class)) {
			diceMock.when(() -> Dice.roll(anyString())).thenReturn(5, 3); // first weapon, then bonus
			int dmg = FightRules.calculateDamage(weaponHolder, attacker, ActionResult.SUCCESS);
			assertThat(dmg).isEqualTo(8);
		}
	}

	/* -------------------------------------------------------------
	 * getPrimaryWeapon – sanity check using real logic
	 * ------------------------------------------------------------- */
	@Test
	void getPrimaryWeapon_RespectsFavoriteHand() {
		// build a minimal data-set so we can call the real method without mocking internals
		CharacterDTO character = mock(CharacterDTO.class);
		CharacterItemDTO sword = mock(CharacterItemDTO.class);
		ItemDTO swordItem = mock(ItemDTO.class);
		when(sword.getItem()).thenReturn(swordItem);
		when(swordItem.getItemType()).thenReturn(ItemType.MELEE_WEAPON);
		when(swordItem.getLength()).thenReturn(90);

		when(character.getItemsIn(BodyPartName.RIGHT_ARM)).thenReturn(List.of(sword));
		when(character.getItemsIn(BodyPartName.LEFT_ARM)).thenReturn(Collections.emptyList());
		when(character.getFavoriteHand()).thenReturn(FavoriteHand.RIGHT);

		assertThat(FightRules.getPrimaryWeapon(character)).isEqualTo(sword);
	}
}