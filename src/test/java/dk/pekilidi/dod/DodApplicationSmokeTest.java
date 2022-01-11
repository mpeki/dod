package dk.pekilidi.dod;

import dk.pekilidi.dod.character.DODCharacterController;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DodApplicationSmokeTest {

  @Autowired
  private DODCharacterController controller;

  @Test
  void contextLoads() {
    assertThat(controller).isNotNull();
  }
}
