package dk.pekilidi.dod.changerequest;

import dk.pekilidi.dod.changerequest.model.ChangeRequest;
import dk.pekilidi.dod.character.CharacterNotFoundException;
import dk.pekilidi.dod.race.RaceNotFoundException;
import java.security.Principal;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/change")
public class ChangeRequestController {

  private ChangeRequestService service;

  @PostMapping("/char/{id}")
  @ResponseBody
  public ChangeRequest handleChangeRequest(Principal principal, @PathVariable String id, @RequestBody ChangeRequest change) {
    return service.submitChangeRequest(id, change, principal.getName());
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void characterNotFoundHandler(CharacterNotFoundException ex) { /* Just need the HttpStatus.NOT_FOUND */ }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void raceNotFoundHandler(RaceNotFoundException ex) {/* Just need the HttpStatus.NOT_FOUND */}
}
