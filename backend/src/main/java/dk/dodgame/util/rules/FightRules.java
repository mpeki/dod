package dk.dodgame.util.rules;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import dk.dodgame.data.CharacterDTO;
import dk.dodgame.data.CharacterItemDTO;
import dk.dodgame.data.CharacterSkillDTO;
import dk.dodgame.data.combat.Fight;
import dk.dodgame.data.combat.Fighter;
import dk.dodgame.domain.action.model.ActionResult;
import dk.dodgame.domain.action.model.Difficulty;
import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.character.model.body.BodyPartName;
import dk.dodgame.domain.item.model.ItemType;
import dk.dodgame.util.Dice;
import dk.dodgame.util.character.CharacterUtil;

@Slf4j
public class FightRules {

    private FightRules() {
    }

    public static int getItemFV(CharacterItemDTO weapon, CharacterDTO character){
        CharacterSkillDTO skillDTO = character.getSkill(weapon.getItemName());
        if(skillDTO == null){
            //Todo this calculation should be based on the item base chance
            return character.getBaseTraitGroupValue(BaseTraitName.DEXTERITY);
        } else {
            return skillDTO.getFv();
        }

    }

    public static CharacterDTO determineInitiative(Fight fight) {

        CharacterDTO opponentA = ((Fighter)fight.getListOfFighters().getFirst()).getCharacter();
        CharacterDTO opponentB = ((Fighter)fight.getListOfFighters().getLast()).getCharacter();
        if(fight.getTurnCounter() == 0) {
            return getMeetingInitiative(opponentA, opponentB);
        } else {
            return getInitiative(opponentA, opponentB);
        }
    }

	public static CharacterDTO getInitiativeLoser(Fight fight) {
		//get the fighter who lost initiative
		if (fight.getCurrentTurn() == null || fight.getCurrentTurn().getInitiativeWinnerId() == null) {
			log.warn("No current turn or initiative winner found in fight {}", fight.getRef());
			return null; // No initiative winner, cannot determine loser
		}
		String initiativeWinnerId = fight.getCurrentTurn().getInitiativeWinnerId();
		return fight.getFighters().values().stream()
				.filter(fighter -> !fighter.getFighterId().equals(initiativeWinnerId)).findFirst().get().getCharacter();
	}

    private static CharacterDTO getMeetingInitiative(CharacterDTO opponentA, CharacterDTO opponentB) {
        log.info("Determining meeting initiative");
        if(getPrimaryWeaponLength(opponentA) == getPrimaryWeaponLength(opponentB)){
            log.info("{} and {} meet in combat", opponentA.getName(), opponentB.getName());
            return getInitiative(opponentA, opponentB);
        }
        CharacterDTO winner = hasLongestPrimaryWeapon(opponentA, opponentB);
        if(winner == null){
            return getRandomOpponent(opponentA, opponentB);
        }
        log.info("{} won initiative due to weapon length advantage", winner.getName());
        return winner;
    }

    private static CharacterDTO getInitiative(CharacterDTO opponentA, CharacterDTO opponentB) {
        CharacterDTO winner = hasHighestBaseTrait(opponentA, opponentB, BaseTraitName.DEXTERITY);
        if(winner == null){
            return getRandomOpponent(opponentA, opponentB);
        }
        log.info("{} won initiative due to being faster", winner.getName());
        return winner;
    }

    public static CharacterDTO hasHighestBaseTrait(CharacterDTO opponentA, CharacterDTO opponentB, BaseTraitName baseTraitName) {
        if (opponentA.getBaseTraitValue(baseTraitName) > opponentB.getBaseTraitValue(baseTraitName)) {
            return opponentA;
        } else if(opponentA.getBaseTraitValue(baseTraitName) < opponentB.getBaseTraitValue(baseTraitName)) {
            return opponentB;
        } else {
            return null;
        }
    }

    public static CharacterDTO hasLongestPrimaryWeapon(CharacterDTO opponentA, CharacterDTO opponentB) {
        if (getPrimaryWeaponLength(opponentA) > getPrimaryWeaponLength(opponentB)) {
            return opponentA;
        } else if (getPrimaryWeaponLength(opponentA) < getPrimaryWeaponLength(opponentB)) {
            return opponentB;
        }
        return null;
    }

    public static int getPrimaryWeaponLength(CharacterDTO character){
        return getPrimaryWeapon(character).getItem().getLength();
    }

    public static CharacterItemDTO getPrimaryWeapon(CharacterDTO character) {
        List<CharacterItemDTO> rightHandItems = character.getItemsIn(BodyPartName.RIGHT_ARM);
        List<CharacterItemDTO> leftHandItems = character.getItemsIn(BodyPartName.LEFT_ARM);
        CharacterItemDTO rightHandItem = rightHandItems.stream()
                .filter(item -> item.getItem().getItemType() == ItemType.MELEE_WEAPON)
                .findFirst()
                .orElse(null);
        CharacterItemDTO leftHandItem = leftHandItems.stream()
                .filter(item -> item.getItem().getItemType() == ItemType.MELEE_WEAPON)
                .findFirst()
                .orElse(null);

        return switch (character.getFavoriteHand()) {
            case LEFT -> leftHandItem;
            case RIGHT -> rightHandItem;
            case DOUBLE, AMBIDEXTROUS -> findFirstStrikeWeapon(rightHandItem, leftHandItem);
        };
    }

	public static CharacterItemDTO getWeaponFor(CharacterDTO character, BodyPartName bodyPartName) {
		List<CharacterItemDTO> items = character.getItemsIn(bodyPartName);
		if (items.isEmpty()) {
			return null; // No weapon found in the specified body part
		}
		return items.stream()
				.filter(item -> item.getItem().getItemType() == ItemType.MELEE_WEAPON)
				.findFirst()
				.orElse(null); // Return the first melee weapon found
	}

    private static CharacterItemDTO findFirstStrikeWeapon(CharacterItemDTO rightHandWeapon, CharacterItemDTO leftHandWeapon) {
        if(rightHandWeapon == null && leftHandWeapon == null) {
            return null;
        } else if (rightHandWeapon == null) {
            return leftHandWeapon;
        } else if (leftHandWeapon == null) {
            return rightHandWeapon;
        } else {
            return rightHandWeapon.getItem().getLength() >= leftHandWeapon.getItem().getLength() ? rightHandWeapon : leftHandWeapon;
        }
    }

    public static CharacterItemDTO getSecondaryWeapon(CharacterDTO character) {
        List<CharacterItemDTO> rightHandItems = character.getItemsIn(BodyPartName.RIGHT_ARM);
        List<CharacterItemDTO> leftHandItems = character.getItemsIn(BodyPartName.LEFT_ARM);
        CharacterItemDTO rightHandItem = rightHandItems.stream()
                .filter(item -> item.getItem().getItemType() == ItemType.MELEE_WEAPON)
                .findFirst()
                .orElse(null);
        CharacterItemDTO leftHandItem = leftHandItems.stream()
                .filter(item -> item.getItem().getItemType() == ItemType.MELEE_WEAPON)
                .findFirst()
                .orElse(null);

        return switch (character.getFavoriteHand()) {
            case LEFT -> rightHandItem;
            case RIGHT -> leftHandItem;
            case DOUBLE, AMBIDEXTROUS ->
                    rightHandItem.getItem().getLength() >= leftHandItem.getItem().getLength() ? leftHandItem : rightHandItem;
        };
    }


    public static CharacterDTO getRandomOpponent(CharacterDTO opponentA, CharacterDTO opponentB){
        CharacterDTO randomOpponent = Dice.roll("1t2") == 1 ? opponentA : opponentB;
        log.info("{} was lucky!", randomOpponent.getName());
        return randomOpponent;
    }

	public static int calculateDamage(CharacterItemDTO primaryWeapon, CharacterDTO attackerChar, ActionResult actionResult) {

		int weaponDamage;
		int damageBonus;

		if(actionResult == ActionResult.MASTERFUL || actionResult == ActionResult.PERFECT) {
			weaponDamage = Dice.max(primaryWeapon.getItem().getDamage());
			damageBonus = Dice.max(attackerChar.getDamageBonus());
		} else {
			weaponDamage = Dice.roll(primaryWeapon.getItem().getDamage());
			damageBonus = Dice.roll(attackerChar.getDamageBonus());
		}
		return weaponDamage + damageBonus;
	}

	public static int getModifierForResult(ActionResult result) {
		return switch (result) {
			case PERFECT -> -10;
			case MASTERFUL -> -5;
			case SUCCESS -> 0;
			case FAILURE, FUMBLE -> 0; // No modifier for failure or fumble
			default -> 0; // Default case
		};
	}

	public static void logFight(Fight fight, String... stats) {

		log.info("Fight reference: {}", fight.getRef());
		fight.getFighters().forEach((id, fighter) -> {
			CharacterDTO character = fighter.getCharacter();
			CharacterUtil.logCharacter(character);
		});

		stats = stats.length == 0 ? new String[] {"TURN", "PHASE"} : stats;
		for (String stat : stats) {
			if(stat.equalsIgnoreCase("TURN")){
				log.info("Turn Counter: {}", fight.getTurnCounter());
			}
			if(stat.equalsIgnoreCase("PHASE")){
				log.info("Fight Phase: {}", fight.getFightPhase());
			}

		}

	}

	public static int calculateModifier() {
		// This method is a placeholder for any future logic that might be needed to calculate a modifier.
		return 0;
	}

	public static Difficulty calculateDifficulty(Fighter fighter) {
		CharacterDTO character = fighter.getCharacter();
		int totalCurrentHP = CharacterUtil.getTotalHitPoints(character.getBodyParts().get(BodyPartName.TOTAL));
		if(fighter.isDowned()){
			log.debug("Character {} is downed, setting difficulty to HARD", character.getName());
			return Difficulty.HARD;
		}
		if(totalCurrentHP <= 3) {
			log.debug("Character {} has {} hit points left, setting difficulty to VERY_HARD", character.getName(), totalCurrentHP);
			return Difficulty.VERY_HARD;
		}
		return Difficulty.NORMAL;
	}
}