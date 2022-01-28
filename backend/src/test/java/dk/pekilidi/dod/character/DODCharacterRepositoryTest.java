package dk.pekilidi.dod.character;

import dk.pekilidi.dod.character.model.DODCharacter;
import dk.pekilidi.dod.character.model.Race;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest(excludeAutoConfiguration = LiquibaseAutoConfiguration.class)
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
    DODCharacter DODCharacter = repository.findByName("kyron");
    Assertions.assertThat(DODCharacter.getName()).isEqualTo("kyron");
    Assertions.assertThat(DODCharacter.getRace().getName()).isEqualTo("tiefling");
  }

}
