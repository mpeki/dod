package dk.pekilidi.dod.skill;

import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.skill.model.Group;
import dk.pekilidi.dod.skill.model.Skill;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface SkillRepository extends CrudRepository<Skill, Long> {
  List<Skill> findByGroup(Group group);

  List<Skill> findByBaseChance(BaseTraitName baseChance);

  List<Skill> findByGroupAndBaseChance(Group group, BaseTraitName baseChance);
}
