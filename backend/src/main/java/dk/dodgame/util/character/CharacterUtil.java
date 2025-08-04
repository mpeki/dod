package dk.dodgame.util.character;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import dk.dodgame.data.BaseTraitDTO;
import dk.dodgame.data.BodyPartDTO;
import dk.dodgame.data.CharacterDTO;
import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.character.model.CharacterState;
import dk.dodgame.domain.character.model.body.BodyPartName;

@Slf4j
public class CharacterUtil {

	private CharacterUtil() {
		// Utility class, no instantiation allowed
	}

	public static int getTotalHitPoints(BodyPartDTO totalHp) {
		return totalHp.getCurrentHP();
	}

	public static int calculateTotalHPDamage(BodyPartName bodyPartName, int damage) {
		if (bodyPartName == BodyPartName.TOTAL) {
			return damage;
		}
		return switch (bodyPartName) {
			case HEAD, CHEST, STOMACH -> damage;
			case RIGHT_ARM, LEFT_ARM, RIGHT_LEG, LEFT_LEG -> damage / 2;
			default -> 0;
		};
	}

	public static CharacterState determineCharacterState(CharacterDTO character){

		BaseTraitDTO constitution = character.getBaseTraits().get(BaseTraitName.CONSTITUTION);
		BodyPartDTO totalHp = character.getBodyParts().get(BodyPartName.TOTAL);
		BodyPartDTO head = character.getBodyParts().get(BodyPartName.HEAD);
		BodyPartDTO chest = character.getBodyParts().get(BodyPartName.CHEST);
		BodyPartDTO stomach = character.getBodyParts().get(BodyPartName.STOMACH);

		// Check if the character is dead
		if (totalHp.getCurrentHP() < - (constitution.getCurrentValue()) ||
				(head.getCurrentHP() < - (head.getMaxHP())) ||
				chest.getCurrentHP() < - (chest.getMaxHP()) ||
				stomach.getCurrentHP() < - (stomach.getMaxHP())) {
			return CharacterState.DEAD;
		}

		// Check if the character is incapacitated
		if (head.getCurrentHP() <= 0 || chest.getCurrentHP() <= 0 || stomach.getCurrentHP() <= 0 || totalHp.getCurrentHP() <= 0) {
			return CharacterState.INCAPACITATED;
		}

		// All other cases, the character is in play
		return CharacterState.IN_PLAY;
	}

	public static void logCharacter(CharacterDTO character, String... stats) {
		stats = stats.length == 0 ? new String[] {"NAME", "HEALTH", "STATE"} : stats;
		for (String stat : stats) {
			if("NAME".equalsIgnoreCase(stat)) {
				log.info("Character Name: {}", character.getName());
			}
			if ("HEALTH".equalsIgnoreCase(stat)) {
				log.info("Current Total HP: {} ({})", character.getBodyParts().get(BodyPartName.TOTAL).getCurrentHP(), character.getBodyParts().get(BodyPartName.TOTAL).getMaxHP());
				log.info("Current Head HP: {} ({})", character.getBodyParts().get(BodyPartName.HEAD).getCurrentHP(), character.getBodyParts().get(BodyPartName.HEAD).getMaxHP());
				log.info("Current Right Arm HP: {} ({})", character.getBodyParts().get(BodyPartName.RIGHT_ARM).getCurrentHP(), character.getBodyParts().get(BodyPartName.RIGHT_ARM).getMaxHP());
				log.info("Current Left Arm HP: {} ({})", character.getBodyParts().get(BodyPartName.LEFT_ARM).getCurrentHP(), character.getBodyParts().get(BodyPartName.LEFT_ARM).getMaxHP());
				log.info("Current Chest HP: {} ({})", character.getBodyParts().get(BodyPartName.CHEST).getCurrentHP(), character.getBodyParts().get(BodyPartName.CHEST).getMaxHP());
				log.info("Current Stomach HP: {} ({})", character.getBodyParts().get(BodyPartName.STOMACH).getCurrentHP(), character.getBodyParts().get(BodyPartName.STOMACH).getMaxHP());
				log.info("Current Right Leg HP: {} ({})", character.getBodyParts().get(BodyPartName.RIGHT_LEG).getCurrentHP(), character.getBodyParts().get(BodyPartName.RIGHT_LEG).getMaxHP());
				log.info("Current Left Leg HP: {} ({})", character.getBodyParts().get(BodyPartName.LEFT_LEG).getCurrentHP(), character.getBodyParts().get(BodyPartName.LEFT_LEG).getMaxHP());
			}
			if("STATE".equalsIgnoreCase(stat)) {
				log.info("State: {}", character.getState());
			}
		}
	}

	public static void checkBleeding(List<CharacterDTO> listCharacters) {
		log.info("Checking for bleeding characters...");
		for (CharacterDTO character : listCharacters) {
			int bloodLoss = character.getBleeding();
			if (bloodLoss > 0) {
				log.info("Character {} is bleeding with {} HP", character.getName(), bloodLoss);
				BodyPartDTO totalHp = character.getBodyParts().get(BodyPartName.TOTAL);
				int newHp = totalHp.getCurrentHP() - character.getBleeding();
				totalHp.setCurrentHP(newHp);
				log.info("New Total HP after bleeding: {}", newHp);
			}
		}
	}
}
