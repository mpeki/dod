package dk.pekilidi.dod.character;

import static dk.pekilidi.dod.character.model.BaseTraitName.STRENGTH;
import static org.assertj.core.api.Assertions.assertThat;

import dk.pekilidi.dod.DodApplication;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.RaceDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DodApplication.class)
@Tag("regression")
class DODCharacterServiceWithRulesTest {

  @Autowired
  private CharacterService charService;

  @Test
  void createCharacterReturnChar() {
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").race(new RaceDTO("human")).build();
    CharacterDTO newBeing = charService.createCharacter(testChar);
    assertThat(newBeing.getBaseTraits()).isNotNull();
    assertThat(newBeing.getBaseTraits()).isNotEmpty();
    assertThat(newBeing.getBaseTraits().get(STRENGTH).getCurrentValue()).isGreaterThanOrEqualTo(3);
    assertThat(newBeing.getBaseTraits().get(STRENGTH).getCurrentValue()).isLessThanOrEqualTo(18);
    assertThat(newBeing.getHeroPoints()).isEqualTo(-1);
    assertThat(newBeing.getRace().getName()).isEqualTo("human");
  }

  @Test
  void createHeroReturnHero() {
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").hero(true).race(new RaceDTO("human")).build();
    CharacterDTO newBeing = charService.createCharacter(testChar);
    assertThat(newBeing.getBaseTraits()).isNotNull();
    assertThat(newBeing.getBaseTraits()).isNotEmpty();
    assertThat(newBeing.getBaseTraits().get(STRENGTH).getCurrentValue()).isGreaterThanOrEqualTo(7);
    assertThat(newBeing.getBaseTraits().get(STRENGTH).getCurrentValue()).isLessThanOrEqualTo(18);
    assertThat(newBeing.getHeroPoints()).isZero();
    assertThat(newBeing.getRace().getName()).isEqualTo("human");
  }

  @Test
  void createdCharacterHasBody() {
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").hero(true).race(new RaceDTO("human")).build();
    charService.createCharacter(testChar);
    assertThat(testChar.getBodyParts()).isNotEmpty();
  }

  @Test
  void foundCharacterHasBody() {
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").hero(true).race(new RaceDTO("human")).build();
    CharacterDTO newBeing = charService.createCharacter(testChar);
    newBeing = charService.findCharacterById(newBeing.getId());
    assertThat(newBeing.getBodyParts()).isNotEmpty();
  }

  @Test
  void getCharacterNonExistingRaceThrowsException() {
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").race(new RaceDTO("hobbit")).build();
    RaceNotFoundException thrown = Assertions.assertThrows(RaceNotFoundException.class, () -> {
      CharacterDTO newBeing = charService.createCharacter(testChar);
    });
  }
}
