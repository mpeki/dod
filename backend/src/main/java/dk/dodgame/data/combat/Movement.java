package dk.dodgame.data.combat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Movement {
    private int speed;
    private Direction direction;
}
