package dk.dodgame;

import static org.assertj.core.api.Assertions.assertThat;

import dk.dodgame.domain.character.CharacterController;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Tag("regression")
class DodApplicationSmokeTest {

  @Autowired
  private CharacterController controller;

  @Test
  void contextLoads() {
    assertThat(controller).isNotNull();
  }
}
