package dk.dodgame.data.combat;

import dk.dodgame.data.DODFact;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Builder
@Setter
@Getter
public class Fight implements DODFact {
    Map<String,List<Occupant>> sides;
    @Builder.Default
    private int turnCounter = 0;
    @Builder.Default
    private FightState fightPhase = FightState.NEW;
    private List<Turn> turns;
}
