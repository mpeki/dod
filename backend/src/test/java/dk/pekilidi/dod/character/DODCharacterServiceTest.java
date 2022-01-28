package dk.pekilidi.dod.character;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dk.pekilidi.dod.character.data.BaseTraitDTO;
import dk.pekilidi.dod.character.data.CharacterDTO;
import dk.pekilidi.dod.character.data.RaceDTO;
import dk.pekilidi.dod.character.model.DODCharacter;
import dk.pekilidi.dod.character.model.Race;

import dk.pekilidi.utils.RandomObjectFiller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DODCharacterServiceTest {

  private CharacterRepository charRepo;
  private DODCharacterService charService;

  @BeforeEach
  void setMockOutput() {
    charRepo = mock(CharacterRepository.class);
    charService = new DODCharacterService(charRepo);
    when(charRepo.findByName("abe")).thenReturn(null);
  }

  @Test
  void getCharacterReturnChar() throws Exception {
    DODCharacter testChar = new RandomObjectFiller().createAndFill(DODCharacter.class);
    testChar.setRace(new Race(null,"tiefling",null));
    testChar.setName("kyron");
    given(charRepo.findByName("kyron")).willReturn(testChar);
    DODCharacter being = charService.getCharacterByName("kyron");
    assertThat(being.getName()).isEqualTo("kyron");
    assertThat(being.getRace().getName()).isEqualTo("tiefling");
  }

  @Test
  void addDuplicateBaseTrait(){
    CharacterDTO testChar = new CharacterDTO("bilbo",new RaceDTO("human", null), null);
    testChar.addBaseTrait(new BaseTraitDTO(BaseTraitName.DEXTERITY,0,0,0));
    assertThat(testChar.getBaseTraits()).hasSize(1);
    testChar.addBaseTrait(new BaseTraitDTO(BaseTraitName.DEXTERITY,0,0,0));
    assertThat(testChar.getBaseTraits()).hasSize(1);
  }

  @Test
  void testPojoMethods() throws Exception {
    RandomObjectFiller objFiller = new RandomObjectFiller();
    DODCharacter testChar = objFiller.createAndFill(DODCharacter.class);
    Race testRace = objFiller.createAndFill(Race.class);
    testRace.setName("tiefling");
    testChar.setRace(testRace);
    testChar.setName("kyron");

    given(charRepo.findByName("kyron")).willReturn(testChar);
    DODCharacter being = charService.getCharacterByName("kyron");
    assertThat(being)
            .isEqualTo(testChar)
            .hasToString(testChar.toString());
  }

  @Test
  void getCharacterReturnCharNotFound() throws Exception {
    assertThrows(CharacterNotFoundException.class, () -> {
      charService.getCharacterByName("abe");
    });
  }

}
