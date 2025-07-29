package dk.dodgame.util.rules;

import static dk.dodgame.util.rules.RulesUtil.determineBodyPartName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import dk.dodgame.data.CharacterDTO;
import dk.dodgame.data.CharacterItemDTO;
import dk.dodgame.data.combat.Fight;
import dk.dodgame.data.combat.FightState;
import dk.dodgame.data.combat.Fighter;
import dk.dodgame.data.combat.Turn;
import dk.dodgame.domain.action.model.*;
import dk.dodgame.domain.character.model.CharacterState;
import dk.dodgame.domain.character.model.body.BodyPartName;
import dk.dodgame.util.Dice;
import dk.dodgame.util.character.CharacterUtil;

@Slf4j
public class MeleeCombatRules {

    private MeleeCombatRules() {
    }

    public static void doHit(MeleeWeaponAttackAction attackAction){
		Fighter attacker = (Fighter) attackAction.getActor();
		Fighter target = (Fighter) attackAction.getTarget();
		CharacterDTO attackerChar = attacker.getCharacter();
		CharacterItemDTO primaryWeapon = FightRules.getPrimaryWeapon(attackerChar);
		int damage = FightRules.calculateDamage(primaryWeapon, attackerChar, attackAction.getActionResult());
		target.applyDamage(attackAction.getTargetBodyPartName(), damage);

		log.info("{} dealt {} damage to {}'s {}", attackerChar.getName(), damage, target.getCharacter().getName(), attackAction.getTargetBodyPartName().name());
		CharacterUtil.logCharacter(target.getCharacter(), "HEALTH", "STATE");
    }

    public static void determineMeleeActions(Fight fight) {

        for (Actor actor : fight.getListOfFighters()) {
			if((actor instanceof Fighter fighter)) {
				List<FightAction> actions = new ArrayList<>();
				CharacterDTO character = fighter.getCharacter();
				CharacterItemDTO primaryWeapon = FightRules.getPrimaryWeapon(character);
				if (primaryWeapon != null) {
					actions.add(MeleeWeaponAttackAction.builder()
							.fightReference(fight.getRef())
							.meleeWeapon(primaryWeapon)
							.fv(FightRules.getItemFV(primaryWeapon, character))
							.turnReference(fight.getCurrentTurn().getRef())
							.actor(fighter)
							.type(Type.MELEE_WEAPON_ATTACK)
							.build());
					actions.add(MeleeWeaponBlockAction.builder()
							.fightReference(fight.getRef())
							.meleeWeapon(primaryWeapon)
							.fv(FightRules.getItemFV(primaryWeapon, character))
							.turnReference(fight.getCurrentTurn().getRef())
							.actor(fighter)
							.type(Type.MELEE_WEAPON_PARRY)
							.build());
				}

/*
				actions.add(MeleeWeaponDodgeAction.builder()
						.fightReference(fight.getRef())
						.turnReference(fight.getCurrentTurn().getRef())
						.actor(fighter)
						.type(Type.MELEE_DODGE)
						.build());
*/

				fighter.setActions(actions);
			}
        }
    }

    public static FightAction createFightAction(Fight fight) {
        return FightActionDecider.decideAction(fight);
    }

    public static FightAction createFightReaction(Turn turn, MeleeWeaponAction action) {
        if(action instanceof MeleeWeaponAttackAction attack){
			var target = (Fighter)attack.getTarget();
			List<Action> availableReactions;
			if(attack.getActionResult().isSuccess()) {
				availableReactions = turn.getDefensiveActionsFor(target.getFighterId());
			} else {
				availableReactions = turn.getOffensiveActionsFor(target.getFighterId());
			}
			return FightActionDecider.decideAttackReaction(attack, availableReactions);
		}
		return NoFightAction.builder().build();
    }


    public static List<Fighter> determineTarget(Fight fight) {
        Map<String, Fighter> fighters = fight.getFighters();
        Fighter attacker = fight.getFighters().get(fight.getCurrentTurn().getInitiativeWinnerId());

        return fighters.values().stream()
                .filter(f -> !f.equals(attacker))
                .toList();
    }

    public static MeleeWeaponBlockAction meleeBlock(Turn turn, MeleeWeaponAttackAction attackAction, MeleeWeaponBlockAction blockAction) {
        Fighter defendingFighter = (Fighter) blockAction.getActor();
        CharacterDTO defendingCharacter = defendingFighter.getCharacter();
        CharacterItemDTO primaryWeapon = FightRules.getPrimaryWeapon(defendingCharacter);
//        log.info("{} is trying to block with a {}", defendingCharacter.getName(), primaryWeapon.getItemName());
		blockAction.setActionResult(
				SkillRules.testSkill(defendingCharacter.getSkill(primaryWeapon.getItemName()), Dice.roll("1t20"), blockAction.getModifier(),
				blockAction.getDifficulty()));
		if(attackAction.getActionResult().isSuccess() && blockAction.getActionResult().isFailure()) {
//			log.info("Attack was successful and block failed, applying damage");
			MeleeCombatRules.doHit(attackAction);
		} else {
//			log.info("{} successfully blocked the attack", defendingCharacter.getName());
		}

		blockAction.setResultDescription(createActionDescription(blockAction));
		log.info(blockAction.getResultDescription().getShortActionDescription());
		resolveActions(turn, blockAction);
        return blockAction;
    }


    public static MeleeWeaponAttackAction meleeAttack(Turn turn, MeleeWeaponAttackAction action) {
		action.setActionResult(SkillRules.testSkill(action.getFv(), Dice.roll("1t20"), action.getDifficulty()));
		if(action.getActionResult().isSuccess()) {
			BodyPartName targetBodypart = determineBodyPartName(action.getTarget());
			action.setTargetBodyPartName(targetBodypart);
		}
		action.setResultDescription(createActionDescription(action));
		log.info(action.getResultDescription().getShortActionDescription());
		resolveActions(turn, action);
        return action;
    }

	private static void resolveActions(Turn turn, MeleeWeaponAction action) {
		action.setResolved(true);
		action.setTurnSequence(turn.getNextActionSequence());
		if(action.getActor() instanceof Fighter actor) {
			turn.getActionsFor(actor.getFighterId())
				.stream()
					.filter(a -> a.getType().isMeleeWeaponAction() && !a.isResolved())
					.forEach(a -> {
						a.setResolved(true);
						a.setTurnSequence(-1);
						a.setActionResult(ActionResult.SKIPPED);
						log.info("Resolving action: {} to {} ({})", a.getType(), ActionResult.SKIPPED, a.getRef());
					});
		}
	}

	public static void checkForEndOfMelee(Fight fight, Turn turn) {
		if(fight.getCurrentTurn().getActions().stream().allMatch(Action::isResolved)) {
			log.info("All actions in the current turn are resolved, ending melee phase");
			fight.getCurrentTurn().nextPhase();
			if(lastManStanding(fight)) {
				fight.setFightPhase(FightState.DONE);
				fight.getListCharacters().forEach(c -> log.info("{} is {}", c.getName(), c.getState()));
			}
		} else {
			log.info("Not all actions are resolved, continuing melee phase");
		}
	}

	private static boolean lastManStanding(Fight fight) {
		long aliveCount = fight.getListCharacters().stream()
				.filter(c -> c.getState() == CharacterState.IN_PLAY)
				.count();
		return aliveCount == 1;
	}

	private static ActionResultDescription createActionDescription(MeleeWeaponAction action) {
		ActionResultDescription.ActionResultDescriptionBuilder result = ActionResultDescription.builder();

		if(action instanceof MeleeWeaponAttackAction attackAction
				&& attackAction.getActor() instanceof Fighter attacker) {
			result.actorName(attacker.getCharacter().getName());
			if(attackAction.getTarget() instanceof Fighter defender) {
				result.targetName(defender.getCharacter().getName());
				result.targetAreaName(attackAction.getActionResult().isSuccess()
						?  attackAction.getTargetBodyPartName().name()
						: "this air");
			}
			result.actionItemName(action.getMeleeWeapon().getItemName());
			result.actionResult(action.getActionResult().name());
			result.difficulty(action.getDifficulty().name());

			return result.build();
		}
		if(action instanceof MeleeWeaponBlockAction blockAction
				&& blockAction.getActor() instanceof Fighter blocker) {
			result.actorName(blocker.getCharacter().getName());
			result.actionItemName(action.getMeleeWeapon().getItemName());
			result.actionResult(action.getActionResult().name());
			result.difficulty(action.getDifficulty().name());

			return result.build();
		}
		return ActionResultDescription.builder().build();
	}
}