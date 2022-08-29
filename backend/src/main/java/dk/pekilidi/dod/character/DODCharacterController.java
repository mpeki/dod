package dk.pekilidi.dod.character;

import dk.pekilidi.dod.character.data.CharacterDTO;
import dk.pekilidi.dod.character.model.DODCharacter;
import dk.pekilidi.dod.rules.changes.ChangeRequest;
import dk.pekilidi.dod.util.objects.CharacterMapper;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DODCharacterController {

  private DODCharacterService characterService;

  @GetMapping("/char/name/{name}")
  public List<DODCharacter> getCharacters(@PathVariable String name) {
    List<DODCharacter> entities = characterService.getCharactersByName(name);
    CharacterMapper mapper = new CharacterMapper();
    List result = Arrays
        .stream(entities.toArray())
        .map(object -> mapper.map(object, DODCharacter.class))
        .collect(Collectors.toList());

    return result;
  }

  @GetMapping("/char/{id}")
  public CharacterDTO getCharacter(@PathVariable Long id) {
    return characterService.findCharacterById(id);
  }

  @PostMapping("/char/{id}/change")
  @ResponseBody
  public ChangeRequest buyBasetraitIncrease(@PathVariable String id, @RequestBody ChangeRequest change) {
    return characterService.increaseBasetrait(Long.parseLong(id), change);
  }

  @PostMapping("/char")
  CharacterDTO postCharacter(@RequestBody CharacterDTO newCharacter) {
    return characterService.createCharacter(newCharacter);
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void characterNotFoundHandler(CharacterNotFoundException ex) { /* Just need the HttpStatus.NOT_FOUND */ }
}
