package dk.dodgame.domain.skill;

import dk.dodgame.data.SkillDTO;
import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.skill.model.Group;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/skill")
public class SkillController {

  private SkillService skillService;

  @GetMapping
  public List<SkillDTO> findSkills(@RequestParam(required = false) String group,
      @RequestParam(required = false) String baseChance) {

    List<SkillDTO> result;
    if (group != null && !group.equals("") && baseChance != null && !baseChance.equals("")) {
      result = skillService.getSkillsByGroupAndBaseChance(Group.valueOf(group), BaseTraitName.valueOf(baseChance));
    } else if (group != null && !group.equals("")) {
      result = skillService.getSkillsByGroup(Group.valueOf(group));
    } else if (baseChance != null && !baseChance.equals("")) {
      result = skillService.getSkillsByBaseChance(BaseTraitName.valueOf(baseChance));
    } else {
      result = skillService.getSkills();
    }
    return result;
  }

  @GetMapping("/key/{key}")
  public SkillDTO getSkillByKey(@PathVariable String key) {
    return skillService.findSkillByKey(key);
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void skillNotFoundHandler(SkillNotFoundException ex) { /* Just need the HttpStatus.NOT_FOUND */ }
}
