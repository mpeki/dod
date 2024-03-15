package dk.dodgame.data.combat;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Movement {
    private int speed;
    private Direction direction;
}
