package dk.dodgame.data.combat;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import dk.dodgame.data.CharacterDTO;
import dk.dodgame.domain.action.model.Actor;
import dk.dodgame.domain.action.model.FightAction;
import dk.dodgame.domain.character.model.body.BodyPartName;
import dk.dodgame.util.ExcludeFromCoverageReportGenerated;

@Builder
@Data
@EqualsAndHashCode(exclude = {"actions"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
@ToString(exclude = "actions")
@ExcludeFromCoverageReportGenerated
public class Fighter implements Actor {

	private String side;
	private CharacterDTO character;
	private Movement movement;
	private State state;
	private List<FightAction> actions;
	@Builder.Default
	private boolean downed = false;

	public String getFighterId() {
		return character.getId();
	}

	public void changeSpeed(int modifier, Cell cell) {
		int maxSpeed = maxSpeed(cell);
		int newSpeed = movement.getSpeed() + modifier;
		if (newSpeed < 0) {
			return;
		}
		movement.setSpeed(Math.min(newSpeed, maxSpeed));
	}

	private int maxSpeed(Cell cell) {
		return switch (cell.getTerrain()) {
			case AIR -> character.getMovementPoints().getSpeedInAir();
			case WATER, RIVER -> character.getMovementPoints().getSpeedInWater();
			default -> character.getMovementPoints().getSpeedOnLand();
		};
	}

	public void applyDamage(BodyPartName bodyPartName, int damage) {
		int afflictedPartHp = character.applyDamage(bodyPartName, damage);
		if ((bodyPartName == BodyPartName.LEFT_LEG || bodyPartName == BodyPartName.RIGHT_LEG) && afflictedPartHp <= 0) {
			downed = true;
		}
	}

	@Override
	public String getActorType() {
		return "fighter";
	}


	public enum State {
		IN_CLOSE_COMBAT, IN_RANGED_COMBAT, FLEEING
	}
}
