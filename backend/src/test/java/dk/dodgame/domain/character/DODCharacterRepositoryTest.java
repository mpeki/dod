package dk.dodgame.domain.character;

import static org.assertj.core.api.Assertions.assertThat;

import dk.dodgame.domain.character.CharacterNotFoundException;
import dk.dodgame.domain.character.CharacterRepository;
import dk.dodgame.domain.character.model.CharacterSkill;
import dk.dodgame.domain.character.model.DODCharacter;
import dk.dodgame.domain.race.model.Race;
import dk.dodgame.domain.skill.SkillKey;
import java.util.Optional;
import org.assertj.core.api.Assertions;
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
  void getCharacterReturnsChar() {

    Race race = entityManager.persist(new Race(null, "tiefling", SkillKey.toSkillKey("common"),null));
    DODCharacter testBeing = new DODCharacter();
    testBeing.setName("kyron");
    testBeing.setRace(race);
    DODCharacter character = entityManager.persistFlushFind(testBeing);
    DODCharacter dodCharacter = repository.findById(character.getId()).orElseThrow(CharacterNotFoundException::new);
    assertThat(dodCharacter.getName()).isEqualTo("kyron");
    Assertions.assertThat(dodCharacter.getRace().getName()).isEqualTo("tiefling");
  }

  @Test
  void findAllReturnsVokan() {
    for (DODCharacter aChar : repository.findAll()) {
      assertThat(aChar.getName()).startsWith("Vokan");
    }
  }

  @Test
  void saveCharacterReturnsPaul() throws Exception {

    Optional<DODCharacter> characterOptional = repository.findById("123");
    assertThat(characterOptional).isNotEmpty().isPresent();

    DODCharacter dodCharacter = characterOptional.get();
    assertThat(dodCharacter.getName()).isEqualTo("Vokan");
    Assertions.assertThat(dodCharacter.getRace().getName()).isEqualTo("human");
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
    assertThat(dodCharacter.getName()).isEqualTo("Vokan");
    Assertions.assertThat(dodCharacter.getRace().getName()).isEqualTo("human");

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
