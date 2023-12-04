package dk.dodgame.domain.race;

import dk.dodgame.data.RaceDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/race")
public class RaceController {

  private RaceService raceService;

  @GetMapping
  public List<RaceDTO> fetchRaces() {
    return raceService.fetchRaces();
  }

  @GetMapping("/name/{name}")
  public RaceDTO findRaceByName(@PathVariable String name) {
    return raceService.getRaceByName(name);
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void raceNotFoundHandler(RaceNotFoundException ex) {/* Just need the HttpStatus.NOT_FOUND */}
}
