package dk.pekilidi.dod.character;

import static dk.pekilidi.utils.BaseTestUtil.TEST_OWNER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.character.model.DODCharacter;
import dk.pekilidi.dod.race.model.Race;
import dk.pekilidi.dod.data.BaseTraitDTO;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.RaceDTO;
import dk.pekilidi.dod.util.character.CharacterMapper;
import dk.pekilidi.utils.RandomObjectFiller;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

@Tag("regression")
class DODCharacterServiceTest {

  private CharacterRepository charRepo;
  private CharacterService charService;

  private final CharacterMapper modelMapper = new CharacterMapper();

  @BeforeEach
  void setMockOutput() {
    charRepo = mock(CharacterRepository.class);
    charService = new CharacterService(charRepo, null, null);
    when(charRepo.findAllByName("NONAME")).thenReturn(List.of());
  }

  @Test
  void getCharacterReturnChar() throws Exception {
    DODCharacter testChar = new RandomObjectFiller().createAndFill(DODCharacter.class);
    testChar.setRace(new Race(null, "tiefling", null));
    testChar.setName("kyron");
    List<DODCharacter> testChars = List.of(testChar);
    given(charRepo.findAllByName("kyron")).willReturn(testChars);
    List<DODCharacter> chars = charService.getCharactersByName("kyron");
    assertThat(chars).hasSize(1);
    DODCharacter being = chars.get(0);
    assertThat(being.getName()).isEqualTo("kyron");
    assertThat(being.getRace().getName()).isEqualTo("tiefling");
  }

  @Test
  void addDuplicateBaseTrait() {
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").race(new RaceDTO("human")).build();
    testChar.addBaseTrait(new BaseTraitDTO(BaseTraitName.DEXTERITY, 0, 0));
    assertThat(testChar.getBaseTraits()).hasSize(1);
    testChar.addBaseTrait(new BaseTraitDTO(BaseTraitName.DEXTERITY, 0, 0));
    assertThat(testChar.getBaseTraits()).hasSize(1);
  }

  @Test
  void addNullNamedBaseTrait() {
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").race(new RaceDTO("human")).build();
    assertThrows(NullPointerException.class, () -> testChar.addBaseTrait(new BaseTraitDTO(null, 0, 0)));
  }

  @Test
  @WithMockUser(username = TEST_OWNER, password = "player", roles = {"player"})
  void testPojoMethods() throws Exception {
    RandomObjectFiller objFiller = new RandomObjectFiller();
    DODCharacter testChar = objFiller.createAndFill(DODCharacter.class);
    Race testRace = objFiller.createAndFill(Race.class);
    testRace.setName("tiefling");
    testChar.setRace(testRace);
    testChar.setName("kyron");
    CharacterDTO testCharDTO = modelMapper.map(testChar, CharacterDTO.class);
    given(charRepo.findByIdAndOwner("123", TEST_OWNER)).willReturn(Optional.of(testChar));
    CharacterDTO being = charService.findCharacterByIdAndOwner("123", TEST_OWNER);
    assertThat(being).isEqualTo(testCharDTO).hasToString(testCharDTO.toString());
  }

  @Test
  void getCharacterByIdReturnCharNotFound() throws Exception {
    assertThrows(CharacterNotFoundException.class, () -> {
      charService.findCharacterByIdAndOwner("18401L", TEST_OWNER);
    });
  }

  @Test
  void getCharacterByNameReturnCharNotFound() throws Exception {
    assertThrows(CharacterNotFoundException.class, () -> {
      charService.getCharactersByName("NONAME");
    });
  }
}
