package dk.dodgame.util;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.github.javafaker.Faker;

import dk.dodgame.data.*;
import dk.dodgame.domain.character.model.AgeGroup;
import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.character.model.CharacterState;
import dk.dodgame.domain.character.model.FavoriteHand;
import dk.dodgame.domain.character.model.body.HumanoidBody;
import dk.dodgame.domain.item.ItemKey;
import dk.dodgame.domain.item.model.ItemType;
import dk.dodgame.domain.skill.SkillKey;
import dk.dodgame.domain.skill.model.Category;
import dk.dodgame.domain.skill.model.Group;

public class CharacterTestUtil {

	private static final Faker faker = new Faker();

	public static RaceDTO createHumanRace() {
		return RaceDTO
				.builder()
				.name("human")
				.characterTemplate(CharacterTemplateDTO.builder()
						.bodyTypeClass(HumanoidBody.class)
						.baseTraitRules(List.of(
										BaseTraitRuleDTO.builder().baseTraitName(BaseTraitName.STRENGTH).baseTraitDieRoll("3t6").build(),
										BaseTraitRuleDTO.builder().baseTraitName(BaseTraitName.PSYCHE).baseTraitDieRoll("3t6").build(),
										BaseTraitRuleDTO.builder().baseTraitName(BaseTraitName.CONSTITUTION).baseTraitDieRoll("3t6").build(),
										BaseTraitRuleDTO.builder().baseTraitName(BaseTraitName.DEXTERITY).baseTraitDieRoll("3t6").build(),
										BaseTraitRuleDTO.builder().baseTraitName(BaseTraitName.SIZE).baseTraitDieRoll("3t6").build()
								)
						).build()
				)
				.build();
	}


	public static CharacterDTO createRandomCharacter() {

		CharacterItemDTO weapon = createRandomCharacterItem();
		return CharacterDTO.builder()
				.race(RaceDTO.builder().name("human").build())
				.id(faker.idNumber().valid())
				.name(faker.gameOfThrones().character())
				.ageGroup(AgeGroup.MATURE)
				.hero(false)
				.skills(Map.of("short.sword", CharacterSkillDTO.builder().fv(10)
						.skill(SkillDTO.builder()
								.key(SkillKey.toSkillKey("short.sword"))
								.category(Category.A)
								.group(Group.COMBAT)
								.build())
								.skillName("short.sword")
								.fv(10)
								.lastUsed(LocalDateTime.now())
						.build()))
				.items(new HashMap<>(Map.of(weapon.getItemName(), weapon)))
				.state(CharacterState.NEW)
				.build();
	}

	public static Map<BaseTraitName, BaseTraitDTO> createRandomBaseTraits() {
		return Map.of(
				BaseTraitName.STRENGTH, BaseTraitDTO.builder().traitName(BaseTraitName.STRENGTH).currentValue(faker.number().numberBetween(3, 20)).build(),
				BaseTraitName.DEXTERITY, BaseTraitDTO.builder().traitName(BaseTraitName.DEXTERITY).currentValue(faker.number().numberBetween(3, 20)).build(),
				BaseTraitName.CHARISMA, BaseTraitDTO.builder().traitName(BaseTraitName.CHARISMA).currentValue(faker.number().numberBetween(3, 20)).build(),
				BaseTraitName.CONSTITUTION, BaseTraitDTO.builder().traitName(BaseTraitName.CONSTITUTION).currentValue(faker.number().numberBetween(3, 20)).build(),
				BaseTraitName.SIZE, BaseTraitDTO.builder().traitName(BaseTraitName.SIZE).currentValue(faker.number().numberBetween(3, 20)).build()
		);
	}

	public static FavoriteHand createRandomFavoriteHand() {
		Random random = new Random();
		return FavoriteHand.values()[random.nextInt(FavoriteHand.values().length)];
	}

	public static ItemDTO createShortSword() {
		return ItemDTO.builder()
				.itemKey(ItemKey.toItemKey("short.sword"))
				.itemType(ItemType.MELEE_WEAPON)
				.damage("1t6+1")
				.length(1)
				.build();
	}

	public static CharacterItemDTO createRandomCharacterItem() {
		return CharacterItemDTO.builder()
				.itemName("short.sword")
				.quantity(1)
				.item(createShortSword())
				.build();
	}
}
