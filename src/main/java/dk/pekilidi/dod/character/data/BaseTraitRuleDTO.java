package dk.pekilidi.dod.character.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseTraitRuleDTO {
    private String baseTraitName;
    private String baseTraitDieRoll;
    private String baseTraitHeroDieRoll;
}
