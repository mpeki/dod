package dk.pekilidi.dod.character;

import dk.pekilidi.dod.DodApplication;
import dk.pekilidi.dod.character.data.BeingDTO;
import dk.pekilidi.dod.character.data.RaceDTO;
import dk.pekilidi.dod.character.model.Being;
import dk.pekilidi.dod.character.model.Race;
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
    BeingDTO testChar = new BeingDTO("bilbo",new RaceDTO("tiefling"));
    Being newBeing = charService.createCharacter(testChar);
    assertThat(newBeing.getName()).isEqualTo("bilbo_NAME_CHANGED");
    assertThat(newBeing.getRace().getName()).isEqualTo("tiefling");
  }

  @Test
  void getCharacterNonExistingRaceThrowsException(){
    BeingDTO testChar = new BeingDTO("bilbo",new RaceDTO("hobbit"));
    RaceNotFoundException thrown = Assertions.assertThrows(RaceNotFoundException.class, () -> {
      Being newBeing = charService.createCharacter(testChar);
    });
  }
}
