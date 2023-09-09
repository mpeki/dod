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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/char")
public class CharacterController {

  private CharacterService characterService;

  @GetMapping("/{id}")
  public CharacterDTO getCharacter(@PathVariable String id) {
    return characterService.findCharacterById(id);
  }

  @DeleteMapping("/{id}")
  public void deleteCharacter(@PathVariable String id) {
    characterService.deleteCharacterById(id);
  }

  @GetMapping
  public List<CharacterDTO> fetchCharacters() {
    return characterService.fetchAllCharacters();
  }

  @GetMapping("/name/{name}")
  public List<CharacterDTO> findCharactersByName(@PathVariable String name) {
    List<DODCharacter> entities = characterService.getCharactersByName(name);
    CharacterMapper mapper = new CharacterMapper();
    return Arrays.stream(entities.toArray()).map(object -> mapper.map(object, CharacterDTO.class)).toList();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  CharacterDTO postCharacter(@RequestBody CharacterDTO newCharacter) {
    return characterService.createCharacter(newCharacter);
  }

  @PostMapping(value = "/bulk/create/{bulkSize}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  List<String> postCharacter(@PathVariable int bulkSize) {
    return characterService.createCharacters(bulkSize, "human");
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void characterNotFoundHandler(CharacterNotFoundException ex) { /* Just need the HttpStatus.NOT_FOUND */ }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void raceNotFoundHandler(RaceNotFoundException ex) {/* Just need the HttpStatus.NOT_FOUND */}
}
