package dk.dodgame.domain.combat;

import static org.mockito.Mockito.when;

import com.github.javafaker.Faker;
import dk.dodgame.DodApplication;
import dk.dodgame.data.CharacterDTO;
import dk.dodgame.data.CharacterItemDTO;
import dk.dodgame.data.ItemDTO;
import dk.dodgame.data.combat.Fight;
import dk.dodgame.data.combat.Fighter;
import dk.dodgame.domain.character.CharacterFactory;
import dk.dodgame.domain.character.CharacterService;
import dk.dodgame.domain.character.model.CharacterState;
import dk.dodgame.domain.character.model.body.BodyPartName;
import dk.dodgame.domain.item.ItemKey;
import dk.dodgame.domain.item.ItemService;
import dk.dodgame.domain.item.model.ItemType;
import dk.dodgame.util.CharacterTestUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = DodApplication.class)
@Tag("regression")
class AutoCombatServiceTest {

    Faker faker = new Faker();

    @Autowired
    private AutoCombatService service;
    @Autowired
	private CharacterFactory characterFactory;

	@MockBean
	private ItemService itemService;

	@BeforeEach
	void setUp() {
		//mock the itemService to return a valid item when requested
		when(itemService.findItemByKey("silver")).thenReturn(ItemDTO.builder()
				.itemType(ItemType.COIN)
				.itemKey(ItemKey.toItemKey("silver")).build());
		when(itemService.findItemByKey("gold")).thenReturn(ItemDTO.builder().itemKey(ItemKey.toItemKey("gold")).build());
	}

	@Test
    void testAutoFight() {
        System.out.println("[DEBUG_LOG] Starting testAutoFight");

		List<Fight> fights = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			CharacterDTO characterOne = characterFactory.createCharacterDTO(CharacterTestUtil.createRandomCharacter(), CharacterTestUtil.createHumanRace());
			characterOne.setState(CharacterState.IN_PLAY);
			CharacterDTO characterTwo = characterFactory.createCharacterDTO(CharacterTestUtil.createRandomCharacter(), CharacterTestUtil.createHumanRace());
			characterTwo.setState(CharacterState.IN_PLAY);
			// Create two fighters with random characters
			Fighter fighterOne = Fighter.builder()
					.side("sideOne")
					.character(characterOne)
					.build();
			setWeapon(fighterOne);

			Fighter fighterTwo = Fighter.builder()
					.side("sideTwo")
					.character(characterTwo)
					.build();
			setWeapon(fighterTwo);

			Map<String, Fighter> fighters = Map.of(fighterOne.getFighterId()
					, fighterOne, fighterTwo.getFighterId(), fighterTwo);

			Fight fight = Fight.builder()
					.fighters(fighters)
					.build();

			fights.add(fight);
		}
		service.beginAutoFight(fights.toArray(new Fight[0]));
		//Print a report of the fight
		System.out.println("[DEBUG_LOG] Fight Report:");
		fights.forEach(fight -> {
			System.out.println("Fight ID: " + fight.getRef());
			fight.getFighters().forEach((id, fighter) -> {
				System.out.println("Fighter ID: " + id);
				System.out.println("Character Name: " + fighter.getCharacter().getName());
				System.out.println("Character State: " + fighter.getCharacter().getState());
				System.out.println("Character damage bonus: " + fighter.getCharacter().getDamageBonus());
				System.out.println("Character HP: " + fighter.getCharacter().getBodyParts().get(BodyPartName.TOTAL));
			});
			System.out.println("Turn Counter: " + fight.getTurnCounter());
			System.out.println("==================================== END FIGHT =========================");
		});
        System.out.println("[DEBUG_LOG] Finished testAutoFight");
    }

	private void setWeapon(Fighter fighter) {

		CharacterDTO character = fighter.getCharacter();
		CharacterItemDTO weapon = CharacterTestUtil.createRandomCharacterItem();
		var favoriteHand = fighter.getCharacter().getFavoriteHand();
		var wield = character.getWield();
		switch (favoriteHand) {
			case LEFT -> wield.put(BodyPartName.LEFT_ARM, List.of(weapon));
			case RIGHT, DOUBLE, AMBIDEXTROUS -> wield.put(BodyPartName.RIGHT_ARM, List.of(weapon));
		}
		character.setWield(wield);
	}

}
