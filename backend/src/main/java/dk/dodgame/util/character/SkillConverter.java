package dk.dodgame.util.character;

import dk.dodgame.data.CharacterSkillDTO;
import dk.dodgame.domain.character.model.CharacterSkill;
import java.util.HashMap;
import java.util.Map;
import org.modelmapper.Converter;

public class SkillConverter {

  public SkillConverter() {
    // TODO document why this constructor is empty
  }

  protected static Converter<Map<String, CharacterSkillDTO>, Map<String, CharacterSkill>> toCharacterSkillEntity =
      context -> {
    Map<String, CharacterSkillDTO> source = context.getSource();
    Map<String, CharacterSkill> target = new HashMap<>();

    for (Map.Entry<String, CharacterSkillDTO> skillEntry : source.entrySet()) {
      CharacterSkillDTO skillDTO = skillEntry.getValue();
      CharacterSkill skillEntity = CharacterSkill.builder()
          .skillKey(skillDTO.getSkill().getKey())
          .fv(skillDTO.getFv())
          .skillPointsSpent(skillDTO.getSkillPointsSpent())
          .itemKey(skillDTO.getItemKey()).build();

      target.put(skillEntry.getKey(), skillEntity);
    }

    return target;
  };

}
