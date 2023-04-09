package dk.pekilidi.dod.race;

import dk.pekilidi.dod.data.RaceDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public class RaceController {

  private RaceService raceService;

  @GetMapping("/race")
  public List<RaceDTO> fetchRaces() {
    return raceService.fetchRaces();
  }

  @GetMapping("/race/name/{name}")
  public RaceDTO findRaceByName(@PathVariable String name) {
    return raceService.getRaceByName(name);
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void raceNotFoundHandler(RaceNotFoundException ex) {/* Just need the HttpStatus.NOT_FOUND */}
}
