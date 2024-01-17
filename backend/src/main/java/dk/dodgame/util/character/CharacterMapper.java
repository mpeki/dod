package dk.dodgame.util.character;

import dk.dodgame.data.CharacterDTO;
import dk.dodgame.data.CharacterSkillDTO;
import dk.dodgame.domain.character.model.CharacterSkill;
import dk.dodgame.domain.character.model.DODCharacter;
import java.util.HashMap;
import java.util.Map;
import org.modelmapper.ModelMapper;

public class CharacterMapper extends ModelMapper {

  public CharacterMapper() {
    super();
    this
        .createTypeMap(CharacterDTO.class, DODCharacter.class)
        .addMappings(mapper -> mapper
            .using(HumanoidBodyConverter.toHumanoidEntity)
            .map(CharacterDTO::getBodyParts, DODCharacter::setBody))
        .addMappings(mapper -> mapper
            .using(SkillConverter.toCharacterSkillEntity)
            .map(CharacterDTO::getSkills, DODCharacter::setSkills));


    this
        .createTypeMap(DODCharacter.class, CharacterDTO.class)
        .addMappings(mapper -> mapper
            .using(HumanoidBodyConverter.toHumanoidDTO)
            .map(DODCharacter::getBody, CharacterDTO::setBodyParts));
  }
}
