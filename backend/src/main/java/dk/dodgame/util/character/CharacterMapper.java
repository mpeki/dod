package dk.dodgame.util.character;

import dk.dodgame.data.CharacterDTO;
import dk.dodgame.domain.character.model.DODCharacter;
import org.modelmapper.ModelMapper;

public class CharacterMapper extends ModelMapper {

  public CharacterMapper() {
    super();
    this
        .createTypeMap(CharacterDTO.class, DODCharacter.class)
        .addMappings(mapper -> mapper
            .using(HumanoidBodyConverter.toHumanoidEntity)
            .map(CharacterDTO::getBodyParts, DODCharacter::setBody));
    this
        .createTypeMap(DODCharacter.class, CharacterDTO.class)
        .addMappings(mapper -> mapper
            .using(HumanoidBodyConverter.toHumanoidDTO)
            .map(DODCharacter::getBody, CharacterDTO::setBodyParts));
  }
}
