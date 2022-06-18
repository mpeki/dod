package dk.pekilidi.dod.character.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterTemplateDTO {
    private String name;
    private List<BaseTraitRuleDTO> baseTraitRules;
}
