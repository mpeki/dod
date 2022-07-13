package dk.pekilidi.dod.character;

import static org.assertj.core.api.Assertions.*;

import dk.pekilidi.dod.character.model.DODCharacter;
import dk.pekilidi.dod.character.model.Race;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class DODCharacterRepositoryTest {

  @Autowired
  private CharacterRepository repository;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  void getCharacterReturnsChar() throws Exception {

    Race race = entityManager.persist(new Race(null,"tiefling",null));
    DODCharacter testBeing = new DODCharacter();
    testBeing.setName("kyron");
    testBeing.setRace(race);
    entityManager.persistFlushFind(testBeing);
    DODCharacter dodCharacter = repository.findById(2L).get();
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

    Optional<DODCharacter> characterOptional = repository.findById(1L);
    assertThat(characterOptional).isNotEmpty();
    assertThat(characterOptional).isPresent();
    DODCharacter dodCharacter = characterOptional.get();
    assertThat(dodCharacter.getName()).isEqualTo("vokan fagerhård");
    assertThat(dodCharacter.getRace().getName()).isEqualTo("human");
    dodCharacter.setName("Paul");
    repository.save(dodCharacter);
    characterOptional = repository.findById(1L);
    assertThat(characterOptional).isNotEmpty();
    assertThat(characterOptional).isPresent();
    dodCharacter = characterOptional.get();
    assertThat(dodCharacter.getName()).isEqualTo("Paul");

  }


}
