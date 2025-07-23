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

@Builder
@Data
@EqualsAndHashCode(exclude = { "actions" })
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
@ToString(exclude = "actions")
public class Fighter implements Actor {
    private String side;
    private CharacterDTO character;
    private Movement movement;
    private List<FightAction> actions;


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

	@Override
	public String getActorType() {
		return "fighter";
	}
}
