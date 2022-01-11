package dk.pekilidi.dod.character;

import dk.pekilidi.dod.character.model.Being;
import dk.pekilidi.dod.character.model.Race;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest(excludeAutoConfiguration = LiquibaseAutoConfiguration.class)
class CharacterRepositoryTest {

  @Autowired
  private CharacterRepository repository;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  void getCharacterReturnsChar() throws Exception {

    Race race = entityManager.persist(new Race(null,"tiefling"));
    entityManager.persistFlushFind(new Being(null,"kyron",race));
    Being character = repository.findByName("kyron");
    Assertions.assertThat(character.getName()).isEqualTo("kyron");
    Assertions.assertThat(character.getRace().getName()).isEqualTo("tiefling");
  }

}
