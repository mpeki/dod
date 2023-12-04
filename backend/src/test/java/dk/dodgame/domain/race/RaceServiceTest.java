package dk.dodgame.domain.race;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import dk.dodgame.domain.race.RaceNotFoundException;
import dk.dodgame.domain.race.RaceRepository;
import dk.dodgame.domain.race.RaceService;
import dk.dodgame.domain.race.model.Race;
import dk.dodgame.data.RaceDTO;
import dk.dodgame.util.RandomObjectFiller;
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