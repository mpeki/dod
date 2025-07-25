package dk.dodgame.data.combat;

import lombok.Builder;
import lombok.Data;

import dk.dodgame.util.ExcludeFromCoverageReportGenerated;

@Builder
@Data
@ExcludeFromCoverageReportGenerated
public class Movement {
    private int speed;
    private Direction direction;
}

//Todo - currently I have two movement classes, clean this up.