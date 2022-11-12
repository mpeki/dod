package dk.pekilidi.dod.character;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import dk.pekilidi.dod.character.model.DODCharacter;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.race.RaceNotFoundException;
import dk.pekilidi.dod.util.character.CharacterMapper;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CharacterController {

  private CharacterService characterService;

  @GetMapping("/char/{id}")
  @CrossOrigin(origins = "http://localhost:3000")
  public CharacterDTO getCharacter(@PathVariable Long id) {
    return characterService.findCharacterById(id);
  }

  @GetMapping("/char")
  @CrossOrigin(origins = "http://localhost:3000")
  public List<CharacterDTO> fetchCharacters(){
    return characterService.fetchAllCharacters();
  }

  @GetMapping("/char/name/{name}")
  @CrossOrigin(origins = "http://localhost:3000")
  public List<CharacterDTO> findCharactersByName(@PathVariable String name) {
    List<DODCharacter> entities = characterService.getCharactersByName(name);
    CharacterMapper mapper = new CharacterMapper();
    return Arrays.stream(entities.toArray()).map(object -> mapper.map(object, CharacterDTO.class)).toList();
  }

  @PostMapping(value = "/char", consumes = MediaType.APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @CrossOrigin(origins = "http://localhost:3000")
  CharacterDTO postCharacter(@RequestBody CharacterDTO newCharacter) {
    return characterService.createCharacter(newCharacter);
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void characterNotFoundHandler(CharacterNotFoundException ex) { /* Just need the HttpStatus.NOT_FOUND */ }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void raceNotFoundHandler(RaceNotFoundException ex) {/* Just need the HttpStatus.NOT_FOUND */}
}
