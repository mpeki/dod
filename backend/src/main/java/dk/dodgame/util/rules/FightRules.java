package dk.dodgame.util.rules;

import dk.dodgame.data.CharacterDTO;
import dk.dodgame.data.CharacterItemDTO;
import dk.dodgame.data.combat.Battle;
import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.character.model.body.BodyPartName;
import dk.dodgame.util.Dice;

import java.util.Map;

public class FightRules {

    private FightRules() {
    }

    public static CharacterDTO determineInitiative(CharacterDTO opponentA, CharacterDTO opponentB) {

        if (getPrimaryWeaponLength(opponentA) > getPrimaryWeaponLength(opponentB)) {
            return opponentA;
        } else if (getPrimaryWeaponLength(opponentA) < getPrimaryWeaponLength(opponentB)) {
            return opponentB;
        } else if (opponentA.getBaseTraitValue(BaseTraitName.DEXTERITY) > opponentB.getBaseTraitValue(BaseTraitName.DEXTERITY)) {
            return opponentA;
        } else if (opponentA.getBaseTraitValue(BaseTraitName.DEXTERITY) < opponentB.getBaseTraitValue(BaseTraitName.DEXTERITY)) {
            return opponentB;
        } else {
            return Dice.roll("1t2") == 1 ? opponentA : opponentB;
        }
    }

    public static int getPrimaryWeaponLength(CharacterDTO character){
        return getPrimaryWeapon(character).getItem().getLength();
    }

    public static CharacterItemDTO getPrimaryWeapon(CharacterDTO character) {
        Map<BodyPartName, CharacterItemDTO> wield = character.getWield();
        CharacterItemDTO rightHandItem = wield.get(BodyPartName.RIGHT_ARM);
        CharacterItemDTO leftHandItem = wield.get(BodyPartName.LEFT_ARM);
        return switch (character.getFavoriteHand()) {
            case LEFT -> leftHandItem;
            case RIGHT -> rightHandItem;
            case DOUBLE, AMBIDEXTROUS ->
                    rightHandItem.getItem().getLength() >= leftHandItem.getItem().getLength() ? rightHandItem : leftHandItem;
        };
    }
}