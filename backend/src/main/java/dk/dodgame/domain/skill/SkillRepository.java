package dk.dodgame.domain.skill;

import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.race.model.Race;
import dk.dodgame.domain.skill.model.Group;
import dk.dodgame.domain.skill.model.Skill;
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
