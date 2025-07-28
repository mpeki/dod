package dk.dodgame.util.rules;

import static dk.dodgame.util.rules.MeleeCombatRules.determineTarget;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import dk.dodgame.data.combat.Fight;
import dk.dodgame.data.combat.Fighter;
import dk.dodgame.data.combat.Turn;
import dk.dodgame.domain.action.model.*;

@Slf4j
public class FightActionDecider {

	private FightActionDecider() {
		throw new IllegalStateException("Utility class");
	}

	public static FightAction decideAction(Fight fight) {
		Turn turn = fight.getCurrentTurn();
		Fighter initiate = fight.getFighters().get(turn.getInitiativeWinnerId());
		log.info("{} is deciding an action...", initiate.getCharacter().getName());
		//Here we should run some rules to determine the initial action
		var action = pickInitialTurnAction(turn, initiate);
		if (action instanceof MeleeWeaponAttackAction meleeAction) {
			List<Fighter> targets = determineTarget(fight);
			if (!targets.isEmpty()) {
				meleeAction.setTarget(targets.getFirst()); // using the first target in the list
				meleeAction.setDifficulty(Difficulty.NORMAL);
				meleeAction.setModifier(0);
				if (meleeAction.getTarget() instanceof Fighter target) {
					log.info("{} has decided to attack {} with a melee weapon",
							initiate.getCharacter().getName(), target.getCharacter().getName());
				}
			} else {
				log.warn("No targets found for fighter {}", initiate.getCharacter().getName());
				return NoFightAction.builder().build(); // Return null if no targets are available
			}
		}

		return action;
	}

	private static FightAction pickInitialTurnAction(Turn turn, Fighter initiate) {
		List<Action> availableActions = turn.getOffensiveActionsFor(initiate.getFighterId());
		FightAction selectedAction = null;
		for (Action availableAction : availableActions) {
//			if(availableAction.getType().isMeleeWeaponAction()) {
//				availableAction.setResolved(true);
//			}
			var attackAction = pickAttackAction(availableAction, initiate);
			if(attackAction != null){
				selectedAction = attackAction;
			}
		}
		return selectedAction;
	}

	private static MeleeWeaponAttackAction pickAttackAction(Action action, Fighter initiate) {
		if (action instanceof MeleeWeaponAttackAction attackAction && action.getActionResult() == null) {
			log.info("{} is picking action: {}", initiate.getCharacter().getName(), action.getType());
			return attackAction;
		}
		return null;
	}

	public static FightAction decideAttackReaction(MeleeWeaponAttackAction attack, List<Action> availableReactions) {
		Fighter target = (Fighter) attack.getTarget();
		if (availableReactions.isEmpty()) {
			log.warn("{} has no available reactions, no reaction!", target.getCharacter().getName());
			return NoFightAction.builder().build(); // No reactions available
		}
		if(attack.getActionResult().isSuccess()){
			return handleAttackSuccess(attack, availableReactions);
		} else {
			return handleAttackFailure(attack, availableReactions);
		}
	}

	private static FightAction handleAttackSuccess(MeleeWeaponAttackAction attackAction, List<Action> availableReactions) {
		Fighter attacker = (Fighter) attackAction.getActor();
		Fighter target = (Fighter) attackAction.getTarget();
		log.info("{} is reacting to {} {} attack", target.getCharacter().getName(), attacker.getCharacter().getName(), attackAction.getActionResult());
		for (Action availableReaction : availableReactions) {
			if( availableReaction instanceof MeleeWeaponBlockAction blockAction && blockAction.getType() == Type.MELEE_WEAPON_PARRY) {
				log.info("{} is using a parry reaction", target.getCharacter().getName());
				blockAction.setType(Type.MELEE_WEAPON_PARRY);
				blockAction.setDifficulty(Difficulty.NORMAL);
				blockAction.setModifier(getModifierForReaction(attackAction.getActionResult()));
				return blockAction;
			}
		}
		return NoFightAction.builder().build();
	}

	private static FightAction handleAttackFailure(MeleeWeaponAttackAction attackAction, List<Action> availableReactions) {
		Fighter attacker = (Fighter) attackAction.getActor();
		Fighter target = (Fighter) attackAction.getTarget();
		log.info("{} is reacting to {} {} attack", target.getCharacter().getName(), attacker.getCharacter().getName(), attackAction.getActionResult());
		for (Action availableReaction : availableReactions) {
			if( availableReaction instanceof MeleeWeaponAttackAction attackReaction && attackReaction.getType() == Type.MELEE_WEAPON_ATTACK) {
				log.info("{} is striking back!", target.getCharacter().getName());
				attackReaction.setTarget(attackAction.getActor());
				attackReaction.setType(Type.MELEE_WEAPON_ATTACK);
				attackReaction.setDifficulty(Difficulty.NORMAL);
				attackReaction.setModifier(0);
				attackAction.setReaction(attackReaction);
				return attackReaction;
			}
		}
		return NoFightAction.builder().build();
	}

	private static int getModifierForReaction(ActionResult attackResult) {
		return switch (attackResult) {
			case PERFECT -> -10;
			case MASTERFUL -> -5;
			case SUCCESS -> 0;
			case FAILURE, FUMBLE -> 0; // No modifier for failure or fumble
			default -> 0; // Default case
		};
	}
}
