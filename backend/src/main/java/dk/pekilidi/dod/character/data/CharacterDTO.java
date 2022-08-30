package dk.pekilidi.dod.character.data;

import static dk.pekilidi.dod.character.AgeGroup.MATURE;

import dk.pekilidi.dod.character.AgeGroup;
import dk.pekilidi.dod.character.BaseTraitName;
import dk.pekilidi.dod.character.CharacterState;
import dk.pekilidi.dod.character.model.body.BodyPartName;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CharacterDTO implements DODFact, Serializable {

  private static final long serialVersionUID = -4787939824473814563L;

  private Long id;
  @Default
  private String name = "NONAME";
  @Transient
  private RaceDTO race;
  @Default
  @Transient
  private Map<BaseTraitName, BaseTraitDTO> baseTraits = new EnumMap<>(BaseTraitName.class);
  @Default
  @Transient
  private Map<BodyPartName, BodyPartDTO> bodyParts = new EnumMap<>(BodyPartName.class);
  @Default
  private AgeGroup ageGroup = MATURE;
  private CharacterState state;
  private int baseSkillPoints;
  @Default
  private int heroPoints = -1;
  @Default
  private boolean hero = false;

  public Integer getBaseTraitValue(BaseTraitName baseTraitName) {
    if (baseTraits.containsKey(baseTraitName)) {
      return baseTraits.get(baseTraitName).getValue();
    }
    return -1;
  }

  public void addBaseTrait(BaseTraitDTO baseTrait) {
    baseTraits.putIfAbsent(baseTrait.getTraitName(), baseTrait);
  }

  public void addBodyPart(BodyPartDTO bodyPart) {
    bodyParts.putIfAbsent(bodyPart.getName(), bodyPart);
  }

  public void incrementTrait(BaseTraitName traitName, int by) {
    baseTraits.computeIfPresent(traitName, (k, v) -> {
      v.setValue(v.getValue() + by);
      return v;
    });
  }

  public void decrementTrait(BaseTraitName traitName, int by) {
    baseTraits.computeIfPresent(traitName, (k, v) -> {
      v.setValue(v.getValue() - by);
      return v;
    });
  }

  public void updateBaseTrait(BaseTraitName traitName, int newValue) {
    baseTraits.computeIfPresent(traitName, (k, v) -> {
      v.setValue(newValue);
      return v;
    });
  }
}
