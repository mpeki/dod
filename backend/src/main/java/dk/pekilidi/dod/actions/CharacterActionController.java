package dk.pekilidi.dod.actions;

import dk.pekilidi.dod.actions.model.Action;
import dk.pekilidi.dod.actions.model.Difficulty;
import dk.pekilidi.dod.actions.model.SkillTrainingAction;
import dk.pekilidi.dod.actions.model.Type;
import dk.pekilidi.dod.character.CharacterService;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.skill.SkillNotFoundException;
import dk.pekilidi.dod.skill.SkillService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RequestMapping("api")
public class CharacterActionController {

  private CharacterActionService service;

  private CharacterService characterService;

  private SkillService skillService;

    @Autowired
    public CharacterActionController(CharacterActionService service) {
        this.service = service;
    }

    @Autowired
    public CharacterActionController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @Autowired
    public CharacterActionController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping("/action/training/char/{characterId}/skill/{skillKey}")
  @ResponseBody
  public SkillTrainingAction trainSkill(@PathVariable String characterId, @PathVariable String skillKey) {
    CharacterDTO character = characterService.findCharacterById(characterId);
    Action action = SkillTrainingAction
        .builder()
        .actor(character)
        .difficulty(Difficulty.NORMAL)
        .type(Type.SKILL_TRAINING)
        .skillKey(skillKey)
        .skill(skillService.findSkillByKey(skillKey))
        .build();
    SkillTrainingAction actionAndResult = (SkillTrainingAction) service.doAction(action);
    characterService.save(actionAndResult.getActor());
    return actionAndResult;
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void skillNotFoundHandler(SkillNotFoundException ex) { /* Just need the HttpStatus.NOT_FOUND */ }
}
