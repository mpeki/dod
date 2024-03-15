package dk.dodgame.data.combat;

import dk.dodgame.data.combat.TurnAction;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class Turn {
    private int turnCount;
    private boolean isCurrent;
    private List<TurnAction> actions;
    private TurnPhase phase;
}
