package dk.dodgame.data.combat;

import dk.dodgame.data.CharacterDTO;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Occupant {
    private String side;
    private CharacterDTO character;
    private Movement movement;

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
