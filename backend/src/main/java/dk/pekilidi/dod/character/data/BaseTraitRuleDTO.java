package dk.pekilidi.dod.character.data;

import dk.pekilidi.dod.character.BaseTraitName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseTraitRuleDTO {
    private BaseTraitName baseTraitName;
    private String baseTraitDieRoll;
    private String baseTraitHeroDieRoll;
}
