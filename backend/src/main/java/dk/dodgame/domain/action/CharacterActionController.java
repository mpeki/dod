package dk.dodgame.domain.action;

import dk.dodgame.data.CharacterDTO;
import dk.dodgame.domain.action.model.Action;
import dk.dodgame.domain.action.model.Difficulty;
import dk.dodgame.domain.action.model.SkillTrainingAction;
import dk.dodgame.domain.action.model.Type;
import dk.dodgame.domain.character.CharacterService;
import dk.dodgame.domain.skill.SkillNotFoundException;
import dk.dodgame.domain.skill.SkillService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class CharacterActionController {

  private final CharacterActionService service;

  private final CharacterService characterService;

  private final SkillService skillService;

  @Autowired
  public CharacterActionController(CharacterActionService service, CharacterService characterService,
      SkillService skillService) {
    this.service = service;
    this.characterService = characterService;
    this.skillService = skillService;
  }

  @PostMapping("/action/training/char/{characterId}/skill/{skillKey}")
  @ResponseBody
  public SkillTrainingAction trainSkill(Principal principal, @PathVariable String characterId, @PathVariable String skillKey) {
    CharacterDTO character = characterService.findCharacterByIdAndOwner(characterId, principal.getName());
    Action action = SkillTrainingAction
        .builder()
        .actor(character)
        .difficulty(Difficulty.NORMAL)
        .type(Type.SKILL_TRAINING)
        .skillKey(skillKey)
        .skill(skillService.findSkillByKey(skillKey))
        .build();
    SkillTrainingAction actionAndResult = (SkillTrainingAction) service.doAction(action);
    characterService.save((CharacterDTO) actionAndResult.getActor(), principal.getName());
    return actionAndResult;
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void skillNotFoundHandler(SkillNotFoundException ex) { /* Just need the HttpStatus.NOT_FOUND */ }
}
