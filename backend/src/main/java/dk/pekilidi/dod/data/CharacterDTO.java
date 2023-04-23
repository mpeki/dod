package dk.pekilidi.dod.data;

import static dk.pekilidi.dod.character.model.AgeGroup.MATURE;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import dk.pekilidi.dod.character.model.AgeGroup;
import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.character.model.CharacterState;
import dk.pekilidi.dod.character.model.FavoriteHand;
import dk.pekilidi.dod.character.model.Looks;
import dk.pekilidi.dod.character.model.SocialStatus;
import dk.pekilidi.dod.character.model.body.BodyPartName;
import dk.pekilidi.dod.util.rules.RulesUtil;
import java.io.Serial;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CharacterDTO implements DODFact, Serializable {

  @Serial
  private static final long serialVersionUID = -4787939824473814563L;

  private String id;
  @Default
  private String name = "NONAME";
  private RaceDTO race;
  @Default
  @EqualsAndHashCode.Exclude
  @JsonInclude(Include.NON_EMPTY)
  private Map<BaseTraitName, BaseTraitDTO> baseTraits = new EnumMap<>(BaseTraitName.class);
  @Default
  @EqualsAndHashCode.Exclude
  @JsonInclude(Include.NON_EMPTY)
  private Map<BodyPartName, BodyPartDTO> bodyParts = new EnumMap<>(BodyPartName.class);
  @Default
  private AgeGroup ageGroup = MATURE;
  private CharacterState state;
  private int baseSkillPoints;
  @Default
  private int heroPoints = -1;
  @Default
  private boolean hero = false;
  @Default
  private String damageBonus = "NA";
  private FavoriteHand favoriteHand;
  private SocialStatus socialStatus;
  @Default
  @EqualsAndHashCode.Exclude
  @JsonInclude(Include.NON_EMPTY)
  private Map<String, SkillDTO> skills = new HashMap<>();
  private Looks looks;

  public Integer getBaseTraitValue(BaseTraitName baseTraitName) {
    if (baseTraits.containsKey(baseTraitName)) {
      return baseTraits.get(baseTraitName).getCurrentValue();
    }
    return -1;
  }

  public Integer getBaseTraitGroupValue(BaseTraitName baseTraitName) {
    if (baseTraits.containsKey(baseTraitName)) {
      return baseTraits.get(baseTraitName).getGroupValue();
    }
    return -1;
  }

  public void addSkill(SkillDTO skillDTO) {
    skills.putIfAbsent(skillDTO.getKey().getKeyValue(), skillDTO);
  }

  public void addBaseTrait(BaseTraitDTO baseTrait) {
    baseTraits.putIfAbsent(baseTrait.getTraitName(), baseTrait);
  }

  public void addBodyPart(BodyPartDTO bodyPart) {
    bodyParts.putIfAbsent(bodyPart.getName(), bodyPart);
  }

  public void incrementTrait(BaseTraitName traitName, int by) {
    baseTraits.computeIfPresent(traitName, (k, v) -> {
      int newValue = v.getCurrentValue() + by;
      v.setCurrentValue(newValue);
      v.setGroupValue(RulesUtil.calculateGroupValue(newValue));
      return v;
    });
  }

  public void decrementTrait(BaseTraitName traitName, int by) {
    baseTraits.computeIfPresent(traitName, (k, v) -> {
      int newValue = v.getCurrentValue() - by;
      v.setCurrentValue(newValue);
      v.setGroupValue(RulesUtil.calculateGroupValue(newValue));
      return v;
    });
  }

  public void updateBaseTrait(BaseTraitName traitName, int newValue) {
    baseTraits.computeIfPresent(traitName, (k, v) -> {
      v.setCurrentValue(newValue);
      v.setGroupValue(RulesUtil.calculateGroupValue(newValue));
      return v;
    });
  }
}
