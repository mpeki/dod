package dk.dodgame.data.combat;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import dk.dodgame.data.CharacterDTO;
import dk.dodgame.domain.action.model.Actor;
import dk.dodgame.domain.action.model.FightAction;

@Builder
@Data
@EqualsAndHashCode(exclude = { "actions" })
@ToString(exclude = "actions")
@JsonTypeName("fighter")
public class Fighter implements Actor {
    private String side;
    private CharacterDTO character;
    private Movement movement;
    private List<FightAction> actions;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@Override
	public String getActorType() {
		return "fighter";
	}

    public String getFighterId() {
        return character.getId();
    }

    public void changeSpeed(int modifier, Cell cell){
        int maxSpeed = maxSpeed(cell);
        int newSpeed = movement.getSpeed() + modifier;
        if(newSpeed < 0){
            return;
        }
        movement.setSpeed(Math.min(newSpeed, maxSpeed));
    }

    private int maxSpeed(Cell cell){
        return switch(cell.getTerrain()){
            case AIR -> character.getMovementPoints().getSpeedInAir();
            case WATER, RIVER -> character.getMovementPoints().getSpeedInWater();
            default -> character.getMovementPoints().getSpeedOnLand();
        };
    }
}
