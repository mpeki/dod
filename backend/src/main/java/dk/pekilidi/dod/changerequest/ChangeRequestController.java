package dk.pekilidi.dod.changerequest;

import dk.pekilidi.dod.changerequest.model.ChangeRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ChangeRequestController {

  private ChangeRequestService service;

  @PostMapping("/change/char/{id}")
  @ResponseBody
  public ChangeRequest buyBasetraitIncrease(@PathVariable String id, @RequestBody ChangeRequest change) {
    return service.submitChangeRequest(Long.parseLong(id), change);
  }
}
