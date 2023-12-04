package dk.dodgame.domain.character;

import dk.dodgame.data.CharacterDTO;
import dk.dodgame.data.RaceDTO;
import dk.dodgame.domain.character.model.DODCharacter;
import dk.dodgame.domain.race.RaceService;
import dk.dodgame.system.rule.DroolsService;
import dk.dodgame.util.character.CharacterMapper;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CharacterFactory {

  private static final CharacterMapper modelMapper = new CharacterMapper();
  private final RaceService raceService;
  private final DroolsService ruleService;

  @Autowired
  public CharacterFactory(RaceService raceService, DroolsService ruleService) {
    this.raceService = raceService;
    this.ruleService = ruleService;
  }

  public CharacterDTO createCharacterDTO(CharacterDTO newCharacter) {
    RaceDTO race = raceService.getRaceByName(newCharacter.getRace().getName());
    newCharacter.setRace(race);
    ruleService.executeRulesFor(newCharacter);
    return newCharacter;
  }

  public List<CharacterDTO> createCharacterDTOs(int numberOfCharacters, String raceName) {
    RaceDTO raceDTO = raceService.getRaceByName(raceName);

    return IntStream.range(0, numberOfCharacters).mapToObj(i -> {
      CharacterDTO newCharacter = CharacterDTO.builder()
          .race(raceDTO)
          .name("batchCharacter_" + i)
          .build();
      ruleService.executeRulesFor(newCharacter);
      return newCharacter;
    }).toList();
  }

  public List<DODCharacter> createDODCharacters(List<CharacterDTO> characterDTOS, String owner){
    return characterDTOS.stream()
            .map(dto -> {
              DODCharacter character = modelMapper.map(dto, DODCharacter.class);
              character.setOwner(owner);
              return character;
            })
            .toList();
  }

  public DODCharacter createDODCharacter(CharacterDTO newCharacter, String owner) {
    DODCharacter characterEntity = modelMapper.map(newCharacter, DODCharacter.class);
    characterEntity.setOwner(owner);
    return characterEntity;
  }
}
