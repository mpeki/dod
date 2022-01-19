package dk.pekilidi.dod.character;

import dk.pekilidi.dod.DodApplication;
import dk.pekilidi.dod.character.data.BaseTraitDTO;
import dk.pekilidi.dod.character.data.CharacterDTO;
import dk.pekilidi.dod.character.data.RaceDTO;
import dk.pekilidi.dod.character.model.DODCharacter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DodApplication.class)
class DODCharacterServiceWithRulesTest {

  @Autowired
  private DODCharacterService charService;
  @Autowired
  private CharacterRepository charRepo;

  @Test
  void getCharacterReturnChar(){
    CharacterDTO testChar = new CharacterDTO("bilbo",new RaceDTO("human", null), null);
    DODCharacter newBeing = charService.createCharacter(testChar);
    assertThat(newBeing.getBaseTraits()).isNotNull();
    assertThat(newBeing.getBaseTraits()).isNotEmpty();
    assertThat(newBeing.getRace().getName()).isEqualTo("human");
  }

  @Test
  void getCharacterNonExistingRaceThrowsException(){
    CharacterDTO testChar = new CharacterDTO("bilbo",new RaceDTO("hobbit", null),null);
    RaceNotFoundException thrown = Assertions.assertThrows(RaceNotFoundException.class, () -> {
      DODCharacter newBeing = charService.createCharacter(testChar);
    });
  }
}
