package dk.pekilidi.dod.skill;

import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.data.SkillDTO;
import dk.pekilidi.dod.skill.model.Group;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SkillController {

  private SkillService skillService;

  @GetMapping("/skill")
  public List<SkillDTO> findSkills(@RequestParam(required = false) String group,
      @RequestParam(required = false) String baseChance) {

    List<SkillDTO> result = new ArrayList<>();
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
}
