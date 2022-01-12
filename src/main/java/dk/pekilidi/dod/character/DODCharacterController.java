package dk.pekilidi.dod.character;

import dk.pekilidi.dod.character.data.BeingDTO;
import dk.pekilidi.dod.character.model.Being;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class DODCharacterController {

  private DODCharacterService characterService;

  @GetMapping("/char/{name}")
  public Being getCharacter(@PathVariable String name){
    return characterService.getCharacterByName(name);
  }

  @PostMapping("/char")
  Being postCharacter(@RequestBody BeingDTO newBeing) {
    return characterService.createCharacter(newBeing);
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void characterNotFoundHandler(CharacterNotFoundException ex) { /* Just need the HttpStatus.NOT_FOUND */ }
}
