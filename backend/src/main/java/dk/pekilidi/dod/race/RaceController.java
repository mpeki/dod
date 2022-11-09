package dk.pekilidi.dod.race;

import dk.pekilidi.dod.data.RaceDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RaceController {

  private RaceService raceService;

  @GetMapping("/race")
  @CrossOrigin(origins = "http://localhost:3000")
  public List<RaceDTO> fetchRaces() {
    return raceService.fetchRaces();
  }
}
