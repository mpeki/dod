package dk.pekilidi.dod.character.data;

import dk.pekilidi.dod.character.BaseTraitName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseTraitRuleDTO {
    private BaseTraitName baseTraitName;
    private String baseTraitDieRoll;
    private String baseTraitHeroDieRoll;
}
