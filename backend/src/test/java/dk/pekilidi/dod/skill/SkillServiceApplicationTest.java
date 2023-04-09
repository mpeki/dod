package dk.pekilidi.dod.skill;

import static org.junit.jupiter.api.Assertions.assertThrows;

import dk.pekilidi.dod.DodApplication;
import dk.pekilidi.dod.character.CharacterNotFoundException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Tag("regression")
@SpringBootTest(classes = DodApplication.class)
class SkillServiceApplicationTest {

  @Autowired
  private SkillService service;


  @ParameterizedTest
  @CsvSource({
      "'primary.weapon'",
      "'primary.weapon'",
      "'primary.weapon'",
      "'secondary.weapon'",
      "'skill.that.does.not.exist'",
      "'primary.weapon'"})
  void findSkill(String skillKey){
    if(skillKey.equals("skill.that.does.not.exist")){
      assertThrows(SkillNotFoundException.class, () -> service.findSkillByKey(skillKey));
      SkillKey key = SkillKey.builder().value(skillKey).build();
      assertThrows(SkillNotFoundException.class, () -> service.findSkillByKey(key));
    } else {
      service.findSkillByKey(skillKey);
      service.findSkillByKey(SkillKey.builder().value(skillKey).build());
    }
  }

}
