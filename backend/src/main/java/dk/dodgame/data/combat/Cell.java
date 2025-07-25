package dk.dodgame.data.combat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Cell {
    private Terrain terrain;
    private Fighter fighter; // Can be null if no unit is on this cell
    private boolean obstacle;
    @Builder.Default
    private int movementModifier = 0;


}
