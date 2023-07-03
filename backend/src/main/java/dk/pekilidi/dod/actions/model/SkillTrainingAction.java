package dk.pekilidi.dod.actions.model;

import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.SkillDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SkillTrainingAction extends Action {
  private String skillKey;
  private SkillDTO skill;
}
