package dk.pekilidi.dod.character;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.character.model.DODCharacter;
import dk.pekilidi.dod.character.model.Race;
import dk.pekilidi.dod.data.BaseTraitDTO;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.RaceDTO;
import dk.pekilidi.dod.util.objects.CharacterMapper;
import dk.pekilidi.utils.RandomObjectFiller;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class DODCharacterServiceTest {

  private CharacterRepository charRepo;
  private CharacterService charService;

  private final CharacterMapper modelMapper = new CharacterMapper();

  @BeforeEach
  void setMockOutput() {
    charRepo = mock(CharacterRepository.class);
    charService = new CharacterService(charRepo);
    when(charRepo.findByName("NONAME")).thenReturn(List.of());
  }

  @Test
  void getCharacterReturnChar() throws Exception {
    DODCharacter testChar = new RandomObjectFiller().createAndFill(DODCharacter.class);
    testChar.setRace(new Race(null, "tiefling", null));
    testChar.setName("kyron");
    List<DODCharacter> testChars = List.of(testChar);
    given(charRepo.findByName("kyron")).willReturn(testChars);
    List<DODCharacter> chars = charService.getCharactersByName("kyron");
    assertThat(chars.size()).isEqualTo(1);
    DODCharacter being = chars.get(0);
    assertThat(being.getName()).isEqualTo("kyron");
    assertThat(being.getRace().getName()).isEqualTo("tiefling");
  }

  @Test
  void addDuplicateBaseTrait() {
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").race(new RaceDTO("human")).build();
    testChar.addBaseTrait(new BaseTraitDTO(BaseTraitName.DEXTERITY, 0, 0, 0));
    assertThat(testChar.getBaseTraits()).hasSize(1);
    testChar.addBaseTrait(new BaseTraitDTO(BaseTraitName.DEXTERITY, 0, 0, 0));
    assertThat(testChar.getBaseTraits()).hasSize(1);
  }

  @Test
  void addNullNamedBaseTrait() {
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").race(new RaceDTO("human")).build();
    assertThrows(NullPointerException.class, () -> {
      testChar.addBaseTrait(new BaseTraitDTO(null, 0, 0, 0));
    });
  }

  @Test
  void testPojoMethods() throws Exception {
    RandomObjectFiller objFiller = new RandomObjectFiller();
    DODCharacter testChar = objFiller.createAndFill(DODCharacter.class);
    Race testRace = objFiller.createAndFill(Race.class);
    testRace.setName("tiefling");
    testChar.setRace(testRace);
    testChar.setName("kyron");
    CharacterDTO testCharDTO = modelMapper.map(testChar, CharacterDTO.class);
    given(charRepo.findById(1L)).willReturn(Optional.of(testChar));
    CharacterDTO being = charService.findCharacterById(1L);
    assertThat(being).isEqualTo(testCharDTO).hasToString(testCharDTO.toString());
  }

  @Test
  void getCharacterByIdReturnCharNotFound() throws Exception {
    assertThrows(CharacterNotFoundException.class, () -> {
      charService.findCharacterById(18401L);
    });
  }

  @Test
  void getCharacterByNameReturnCharNotFound() throws Exception {
    assertThrows(CharacterNotFoundException.class, () -> {
      charService.getCharactersByName("NONAME");
    });
  }
}
