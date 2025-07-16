package dk.dodgame.domain.character;

import static dk.dodgame.util.BaseTestUtil.TEST_OWNER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dk.dodgame.data.BaseTraitDTO;
import dk.dodgame.data.CharacterDTO;
import dk.dodgame.data.RaceDTO;
import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.character.model.DODCharacter;
import dk.dodgame.domain.race.RaceRepository;
import dk.dodgame.domain.race.model.Race;
import dk.dodgame.domain.skill.SkillKey;
import dk.dodgame.domain.user.config.ConfigurationService;
import dk.dodgame.util.character.CharacterMapper;
import dk.dodgame.util.RandomObjectFiller;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

@Tag("regression")
class DODCharacterServiceTest {

  private RaceRepository raceRepository;
  private CharacterRepository charRepo;
  private CharacterService charService;

  private final CharacterFactory characterFactory = mock(CharacterFactory.class);
  private final CharacterMapper modelMapper = new CharacterMapper();

  @BeforeEach
  void setMockOutput() {
    charRepo = mock(CharacterRepository.class);
    raceRepository = mock(RaceRepository.class);
    ConfigurationService configService = mock(ConfigurationService.class);
    charService = new CharacterService(characterFactory, charRepo, configService);
    when(configService.resolveMaxNpcsForUser(anyString())).thenReturn(10);
    when(charRepo.findAllByName("NONAME")).thenReturn(List.of());
    when(raceRepository.findByName("noRace")).thenReturn(null);
  }


  @Test
  void createCharacterMaxLimitReached() {
    when(charRepo.countByOwner(anyString())).thenReturn(10);
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").race(new RaceDTO("human")).build();
    assertThrows(MaxCharactersReachedException.class, () -> charService.createCharacter(testChar, TEST_OWNER));
  }


  @Test
  void createBulkCharacterMaxLimitReached() {
    when(charRepo.countByOwner(anyString())).thenReturn(10);
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").race(new RaceDTO("human")).build();
    assertThrows(MaxCharactersReachedException.class, () -> charService.createCharacters(10, "human", TEST_OWNER));
  }



  @Test
  void getCharacterReturnChar() throws Exception {
    DODCharacter testChar = createTestDODCharacter();
    testChar.setRace(new Race(null, "tiefling", SkillKey.toSkillKey("common"), null));
    testChar.setName("kyron");
    List<DODCharacter> testChars = List.of(testChar);
    given(charRepo.findAllByName("kyron")).willReturn(testChars);
    List<DODCharacter> chars = charService.getCharactersByName("kyron");
    assertThat(chars).hasSize(1);
    DODCharacter being = chars.getFirst();
    assertThat(being.getName()).isEqualTo("kyron");
    Assertions.assertThat(being.getRace().getName()).isEqualTo("tiefling");
  }

  @Test
  void addDuplicateBaseTrait() {
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").race(new RaceDTO("human")).build();
    testChar.addBaseTrait(new BaseTraitDTO(BaseTraitName.DEXTERITY, 0, 0));
    Assertions.assertThat(testChar.getBaseTraits()).hasSize(1);
    testChar.addBaseTrait(new BaseTraitDTO(BaseTraitName.DEXTERITY, 0, 0));
    Assertions.assertThat(testChar.getBaseTraits()).hasSize(1);
  }

  @Test
  void addNullNamedBaseTrait() {
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").race(new RaceDTO("human")).build();
    assertThrows(NullPointerException.class, () -> testChar.addBaseTrait(new BaseTraitDTO(null, 0, 0)));
  }

  @Test
  @WithMockUser(username = TEST_OWNER, password = "player", roles = {"player"})
  void testPojoMethods() throws Exception {
    DODCharacter testChar = createTestDODCharacter();
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

  @Test
  @WithMockUser(username = TEST_OWNER, password = "player", roles = {"player"})
  void createCharacterSuccess(){
    CharacterDTO testChar = createTestCharacterDTO();
    DODCharacter testDODChar = createTestDODCharacter();

    when(charRepo.countByOwner(anyString())).thenReturn(4);
    when(charRepo.save(any())).thenReturn(testDODChar);
    when(characterFactory.createCharacterDTO(testChar)).thenReturn(createTestCharacterDTO());

    CharacterDTO characterDTOCreated = charService.createCharacter(testChar, TEST_OWNER);
    assertThat(characterDTOCreated.getId()).isEqualTo(testDODChar.getId());
    assertThat(characterDTOCreated.getId()).isNotEqualTo(testChar.getId());
  }

  @Test
  @WithMockUser(username = TEST_OWNER, password = "player", roles = {"player"})
  void createCharacterNullPointerIfCharIsNull() {
    assertThrows(NullPointerException.class, () -> charService.createCharacter(null, TEST_OWNER));
  }

  @Test
  @WithMockUser(username = TEST_OWNER, password = "player", roles = {"player"})
  void createCharacterNullPointerIfOwnerIsNull() {
    CharacterDTO testCharacter = createTestCharacterDTO();
    assertThrows(NullPointerException.class, () -> charService.createCharacter(testCharacter, null));  }

  @Test
  @WithMockUser(username = TEST_OWNER, password = "player", roles = {"player"})
  void createCharactersSuccess() {

    List<CharacterDTO> testCharDTOs = List.of(createTestCharacterDTO());
    List<DODCharacter> testDODChars = List.of(createTestDODCharacter(),createTestDODCharacter(),createTestDODCharacter(),createTestDODCharacter(),createTestDODCharacter());

    when(charRepo.countByOwner(anyString())).thenReturn(4);
    when(characterFactory.createCharacterDTOs(5, "human")).thenReturn(testCharDTOs);
    when(characterFactory.createDODCharacters(any(), anyString())).thenReturn(testDODChars);
    when(charRepo.saveAll(any())).thenReturn(testDODChars);

    List<String> characterIds = charService.createCharacters(5, "human", TEST_OWNER);
    assertThat(characterIds).hasSize(5);
    //Ensure the testDODChars Id's are present in characterIds (not necessarily in the same order)
    for(DODCharacter testDODChar : testDODChars){
      assertThat(characterIds).contains(testDODChar.getId());
    }
  }

  @Test
  @WithMockUser(username = TEST_OWNER, password = "player", roles = {"player"})
  void createCharactersNullPointerIfNumberCharIsNull() {
    assertThrows(NullPointerException.class, () -> charService.createCharacters(5, "human", null));
  }

  private DODCharacter createTestDODCharacter(){
    RandomObjectFiller objFiller = new RandomObjectFiller();
    DODCharacter testChar = objFiller.createAndFill(DODCharacter.class);
    Race testRace = objFiller.createAndFill(Race.class);
    testRace.setName("tiefling");
    testChar.setRace(testRace);
    testChar.setName("kyron");
    return testChar;
  }

  private CharacterDTO createTestCharacterDTO(){
    DODCharacter testChar = createTestDODCharacter();
    return modelMapper.map(testChar, CharacterDTO.class);
  }
}
