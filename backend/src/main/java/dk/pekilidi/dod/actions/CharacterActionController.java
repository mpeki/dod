package dk.pekilidi.dod.actions;

import dk.pekilidi.dod.actions.model.Action;
import dk.pekilidi.dod.actions.model.Difficulty;
import dk.pekilidi.dod.actions.model.SkillTrainingAction;
import dk.pekilidi.dod.actions.model.Type;
import dk.pekilidi.dod.character.CharacterService;
import dk.pekilidi.dod.data.CharacterDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public class CharacterActionController {

  @Autowired
  private CharacterActionService service;

  @Autowired
  private CharacterService characterService;

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
        .build();
    SkillTrainingAction actionAndResult = (SkillTrainingAction) service.doAction(action);
    characterService.save(actionAndResult.getActor());
    return actionAndResult;
  }
}
