package dk.pekilidi.dod.character;

import dk.pekilidi.dod.character.model.Being;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DODCharacterController {

  private DODCharacterService characterService;

  @GetMapping("/char/{name}")
  private Being getCharacter(@PathVariable String name){
    return characterService.getCharacterByName(name);
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  private void characterNotFoundHandler(CharacterNotFoundException ex) {}
}
