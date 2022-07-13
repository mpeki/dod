package dk.pekilidi.dod.character;

import dk.pekilidi.dod.character.data.CharacterDTO;
import dk.pekilidi.dod.character.model.DODCharacter;
import dk.pekilidi.dod.rules.changes.ChangeRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class DODCharacterController {

  private DODCharacterService characterService;

  @GetMapping("/char/name/{name}")
  public List<DODCharacter> getCharacters(@PathVariable String name){
    return characterService.getCharactersByName(name);
  }

  @GetMapping("/char/{id}")
  public CharacterDTO getCharacter(@PathVariable Long id){
    return characterService.findCharacterById(id);
  }

  @PostMapping("/char/{id}/change")
  @ResponseBody
  public ChangeRequest buyBasetraitIncrease(@PathVariable String id, @RequestBody ChangeRequest change){
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
