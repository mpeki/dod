package dk.pekilidi.dod.race;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dk.pekilidi.dod.character.CharacterNotFoundException;
import dk.pekilidi.dod.character.CharacterRepository;
import dk.pekilidi.dod.character.CharacterService;
import dk.pekilidi.dod.character.model.DODCharacter;
import dk.pekilidi.dod.data.RaceDTO;
import dk.pekilidi.dod.race.model.Race;
import dk.pekilidi.utils.RandomObjectFiller;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class RaceServiceTest {

  private RaceRepository repo;
  private RaceService service;


  @BeforeEach
  void setMockOutput() {
    repo = mock(RaceRepository.class);
    service = new RaceService(repo);
  }

  @Test
  void fetchRaces() throws Exception {
    Race race = new RandomObjectFiller().createAndFill(Race.class);
    race.setName("human");
    List<Race> races = List.of(race);
    given(repo.findAll()).willReturn(races);
    List<RaceDTO> raceDTOList = service.fetchRaces();
    assertThat(raceDTOList.get(0).getName()).isEqualTo("human");
  }

  @Test
  void getRaceByName() throws Exception {
    Race race = new RandomObjectFiller().createAndFill(Race.class);
    race.setName("human");
    given(repo.findByName("human")).willReturn(race);
    RaceDTO humanRace = service.getRaceByName("human");
    assertThat(humanRace.getName()).isEqualTo("human");
  }

  @Test
  void getCharacterByNameReturnCharNotFound() throws Exception {
    assertThrows(RaceNotFoundException.class, () -> {
      service.getRaceByName("NONAME");
    });
  }
}