package dk.pekilidi.dod.character;

import dk.pekilidi.dod.character.data.CharacterDTO;
import dk.pekilidi.dod.character.model.DODCharacter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class DODCharacterController {

  private DODCharacterService characterService;

  @GetMapping("/char/{name}")
  public DODCharacter getCharacter(@PathVariable String name){
    return characterService.getCharacterByName(name);
  }

  @PostMapping("/char")
  DODCharacter postCharacter(@RequestBody CharacterDTO newCharacter) {
    return characterService.createCharacter(newCharacter);
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void characterNotFoundHandler(CharacterNotFoundException ex) { /* Just need the HttpStatus.NOT_FOUND */ }
}
