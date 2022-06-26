package dk.pekilidi.dod.character;

import dk.pekilidi.dod.DodApplication;
import dk.pekilidi.dod.character.data.CharacterDTO;
import dk.pekilidi.dod.character.data.RaceDTO;
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
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").race(new RaceDTO("human", null)).build();
    CharacterDTO newBeing = charService.createCharacter(testChar);
    assertThat(newBeing.getBaseTraits()).isNotNull();
    assertThat(newBeing.getBaseTraits()).isNotEmpty();
    assertThat(newBeing.getRace().getName()).isEqualTo("human");
  }

  @Test
  void getCharacterNonExistingRaceThrowsException(){
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").race(new RaceDTO("hobbit", null)).build();
    RaceNotFoundException thrown = Assertions.assertThrows(RaceNotFoundException.class, () -> {
      CharacterDTO newBeing = charService.createCharacter(testChar);
    });
  }
}
