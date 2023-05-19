package dk.pekilidi.dod.character;

import static org.assertj.core.api.Assertions.assertThat;

import dk.pekilidi.dod.character.model.CharacterSkill;
import dk.pekilidi.dod.character.model.DODCharacter;
import dk.pekilidi.dod.race.model.Race;
import dk.pekilidi.dod.skill.SkillKey;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@Tag("regression")
class DODCharacterRepositoryTest {

  @Autowired
  private CharacterRepository repository;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  void getCharacterReturnsChar() throws Exception {

    Race race = entityManager.persist(new Race(null, "tiefling", null));
    DODCharacter testBeing = new DODCharacter();
    testBeing.setName("kyron");
    testBeing.setRace(race);
    DODCharacter character = entityManager.persistFlushFind(testBeing);
    DODCharacter dodCharacter = repository.findById(character.getId()).get();
    assertThat(dodCharacter.getName()).isEqualTo("kyron");
    assertThat(dodCharacter.getRace().getName()).isEqualTo("tiefling");
  }

  @Test
  void findAllReturnsVokan() {
    for (DODCharacter aChar : repository.findAll()) {
      assertThat(aChar.getName()).startsWith("vokan");
    }
  }

  @Test
  void saveCharacterReturnsPaul() throws Exception {

    Optional<DODCharacter> characterOptional = repository.findById("123");
    assertThat(characterOptional).isNotEmpty().isPresent();

    DODCharacter dodCharacter = characterOptional.get();
    assertThat(dodCharacter.getName()).isEqualTo("vokan fagerhård");
    assertThat(dodCharacter.getRace().getName()).isEqualTo("human");
    dodCharacter.setName("Paul");
    repository.save(dodCharacter);
    characterOptional = repository.findById("123");
    assertThat(characterOptional).isNotEmpty().isPresent();
    dodCharacter = characterOptional.get();
    assertThat(dodCharacter.getName()).isEqualTo("Paul");
  }

  @Test
  void saveCharacter_withSkills() throws Exception {

    Optional<DODCharacter> characterOptional = repository.findById("123");
    assertThat(characterOptional).isNotEmpty().isPresent();

    DODCharacter dodCharacter = characterOptional.get();
    assertThat(dodCharacter.getName()).isEqualTo("vokan fagerhård");
    assertThat(dodCharacter.getRace().getName()).isEqualTo("human");

    dodCharacter.setName("skilled char");
    CharacterSkill skill = CharacterSkill.builder()
        .skillKey(SkillKey.toSkillKey("primary.weapon"))
        .fv(12)
        .experience(0)
        .build();
    dodCharacter.addSkill(skill);
    repository.save(dodCharacter);
    characterOptional = repository.findById("123");
    assertThat(characterOptional).isNotEmpty().isPresent();
    dodCharacter = characterOptional.get();
    assertThat(dodCharacter.getName()).isEqualTo("skilled char");
    assertThat(dodCharacter.getSkills()).containsKey("primary.weapon");
  }


}
