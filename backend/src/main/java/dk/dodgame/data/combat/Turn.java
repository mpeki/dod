package dk.dodgame.data.combat;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

import dk.dodgame.domain.action.model.Action;
import dk.dodgame.domain.action.model.Actor;
import dk.dodgame.util.DoDTsidGenerator;

@Builder
@Data
public class Turn {
	@Builder.Default
	private String ref = DoDTsidGenerator.createReferenceNo("trn");
	private int turnCount;
	private boolean isCurrent;
	@Builder.Default
	private List<Action> actions = new ArrayList<>();
	private TurnPhase phase;
	private String initiativeWinnerId;

	public void nextPhase() {
		switch (phase) {
			case NEW -> this.phase = TurnPhase.DETERMINE_INITIATIVE;
			case DETERMINE_INITIATIVE -> this.phase = TurnPhase.MAGIC;
			case MAGIC -> this.phase = TurnPhase.RANGED_WEAPONS;
			case RANGED_WEAPONS -> this.phase = TurnPhase.MELEE_WEAPONS;
			case MELEE_WEAPONS -> this.phase = TurnPhase.MOVEMENT;
			case MOVEMENT -> this.phase = TurnPhase.DONE;
			case DONE -> this.phase = TurnPhase.NEW;
			default -> throw new IllegalStateException("Unexpected value: " + phase);
		}
	}

	public void addFightActions(List<? extends Actor> actor) {
		for (Actor a : actor) {
			if (a instanceof Fighter fighter) {
				actions.addAll(fighter.getActions());
			}
		}
	}

	public List<Action> getActionsFor(String actorId) {
		List<Action> actorActions = new ArrayList<>();
		for (Action action : actions) {
			if (action.getActor() != null &&
					action.getActor() instanceof Fighter fighter &&
					fighter.getFighterId().equals(actorId) && !action.isResolved()) {
				actorActions.add(action);
			}
		}
		return actorActions;
	}

	public List<Action> getDefensiveActionsFor(String actorId) {
		List<Action> actorActions = new ArrayList<>();
		for (Action action : actions) {
			if (action.getActor() != null &&
					action.getActor() instanceof Fighter fighter &&
					fighter.getFighterId().equals(actorId) &&
					action.getType().isDefensiveAction() && !action.isResolved()) {
				actorActions.add(action);
			}
		}
		return actorActions;
	}

	public List<Action> getOffensiveActionsFor(String actorId) {
		List<Action> actorActions = new ArrayList<>();
		for (Action action : actions) {
			if (action.getActor() != null &&
					action.getActor() instanceof Fighter fighter &&
					fighter.getFighterId().equals(actorId) && action.getType().isAttackAction() && !action.isResolved()) {
				actorActions.add(action);
			}
		}
		return actorActions;
	}


}
