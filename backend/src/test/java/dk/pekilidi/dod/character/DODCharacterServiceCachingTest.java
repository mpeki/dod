package dk.pekilidi.dod.character;

import static dk.pekilidi.utils.BaseTestUtil.TEST_OWNER;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import dk.pekilidi.dod.character.model.AgeGroup;
import dk.pekilidi.dod.character.model.DODCharacter;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.race.RaceRepository;
import dk.pekilidi.dod.race.model.Race;
import dk.pekilidi.dod.rules.DroolsService;
import dk.pekilidi.dod.util.character.CharacterMapper;
import dk.pekilidi.utils.RandomObjectFiller;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureTestDatabase
@Tag("regression")
class DODCharacterServiceCachingTest {

  DODCharacter testCharacter;
  CharacterDTO testCharacterDTO;
  @Autowired
  private CharacterService service;
  @MockBean
  private CharacterRepository charRepo;
  @MockBean
  private RaceRepository raceRepo;
  @MockBean
  private DroolsService ruleService;

  @BeforeEach
  void setup() throws Exception {
    final CharacterMapper modelMapper = new CharacterMapper();
    testCharacter = new RandomObjectFiller().createAndFill(DODCharacter.class);

    testCharacter.setName("kyron");
    testCharacter.setAgeGroup(AgeGroup.MATURE);
    testCharacter.setRace(new Race(null, "human", null));
    testCharacter.setId("1234");
    testCharacterDTO = modelMapper.map(testCharacter, CharacterDTO.class);

    List<DODCharacter> chars = new ArrayList<>(1);
    chars.add(testCharacter);

    given(charRepo.save(testCharacter)).willReturn(modelMapper.map(testCharacter, DODCharacter.class));
    given(charRepo.findAllByName(anyString())).willReturn(chars);
    given(raceRepo.findByName(anyString())).willReturn(Race.builder().name("human").build());
    given(ruleService.executeRulesFor(testCharacterDTO)).willReturn(1);
    service.createCharacter(testCharacterDTO, TEST_OWNER);
  }

  @Test
  void testCharServiceCache() throws Exception {
    service.getCharactersByName("kyron");
    service.getCharactersByName("kyron");
    service.getCharactersByName("kyron");
    verify(charRepo, times(1)).findAllByName("kyron");
  }

  @Test
  void testCharServiceCacheEviction() throws Exception {
    service.getCharactersByName("kyron");
    service.createCharacter(testCharacterDTO, TEST_OWNER); //This evicts the cache
    service.getCharactersByName("kyron");
    service.getCharactersByName("kyron");
    service.getCharactersByName("kyron");
    service.getCharactersByName("kyron");
    verify(charRepo, times(2)).findAllByName("kyron");
  }
}
