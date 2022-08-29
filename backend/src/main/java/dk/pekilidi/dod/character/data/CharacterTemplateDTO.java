package dk.pekilidi.dod.character.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterTemplateDTO {
    private Long id;
    private String name;
    private List<BaseTraitRuleDTO> baseTraitRules;
    private Class bodyTypeClass;
}
