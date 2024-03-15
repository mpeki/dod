package dk.dodgame.data.combat;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Battle {
    private Battlefield field;

    @Builder.Default
    private int turnCounter = 0;
    private List<Fight> fights;
}
