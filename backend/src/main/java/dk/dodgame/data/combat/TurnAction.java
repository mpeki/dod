package dk.dodgame.data.combat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Builder
@Setter
@Getter
public class TurnAction {

    TurnActionType type;
    String description;

}
