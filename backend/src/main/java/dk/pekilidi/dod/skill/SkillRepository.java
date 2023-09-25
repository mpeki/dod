package dk.pekilidi.dod.skill;

import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.race.model.Race;
import dk.pekilidi.dod.skill.model.Group;
import dk.pekilidi.dod.skill.model.Skill;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SkillRepository extends CrudRepository<Skill, Long> {

  List<Skill> findByGroup(Group group);

  List<Skill> findByBaseChance(BaseTraitName baseChance);

  List<Skill> findByGroupAndBaseChance(Group group, BaseTraitName baseChance);

  Skill findByKey(SkillKey key);

  @Query("SELECT s FROM Skill s JOIN s.deniedRaces ar WHERE ar = :race")
  List<Skill> findDeniedSkillsByRace(@Param("race") Race race);

}
