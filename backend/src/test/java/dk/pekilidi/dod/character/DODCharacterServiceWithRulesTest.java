package dk.pekilidi.dod.character;

import dk.pekilidi.dod.DodApplication;
import dk.pekilidi.dod.character.data.CharacterDTO;
import dk.pekilidi.dod.character.data.RaceDTO;
import dk.pekilidi.dod.rules.changes.ChangeRequest;
import dk.pekilidi.dod.rules.changes.ChangeStatus;
import dk.pekilidi.dod.rules.changes.ChangeStatusLabel;
import dk.pekilidi.dod.rules.changes.ChangeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static dk.pekilidi.dod.character.BaseTraitName.*;
import static dk.pekilidi.dod.character.CharacterState.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DodApplication.class)
class DODCharacterServiceWithRulesTest {

  @Autowired
  private DODCharacterService charService;

  @Test
  void createCharacterReturnChar(){
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
  void createHeroReturnHero(){
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
  void foundCharacterHasBody(){
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").hero(true).race(new RaceDTO("human")).build();
    CharacterDTO newBeing = charService.createCharacter(testChar);
    newBeing = charService.findCharacterById(newBeing.getId());
    assertThat(newBeing.getBodyParts()).isNotEmpty();
  }

  @Test
  void buyBasetraitIncrease() {

    CharacterDTO newChar = charService.createCharacter(CharacterDTO.builder()
        .name("tester")
        .hero(true)
        .race(new RaceDTO("human")).build());
    newChar.setState(INIT_COMPLETE);
    newChar.setHeroPoints(2);
    newChar = charService.save(newChar);
    int orgStrength = newChar.getBaseTraits().get(STRENGTH).getCurrentValue();
    ChangeRequest change = ChangeRequest.builder()
        .changeDescription("Increase strength")
        .changeType(ChangeType.BASE_TRAIT)
        .changeKey(STRENGTH)
        .modifier(1).build();
    ChangeRequest changeRequest = charService.increaseBasetrait(newChar.getId(), change);
    CharacterDTO result = (CharacterDTO)changeRequest.getObjectAfterChange();
    assertThat(orgStrength).isNotEqualTo(result.getBaseTraits().get(STRENGTH).getCurrentValue());
    assertThat(result.getHeroPoints()).isZero();
  }

  @Test
  void buyBaseTraitIncreaseRejected() {

    CharacterDTO newChar = charService.createCharacter(CharacterDTO.builder()
        .name("tester")
        .hero(true)
        .race(new RaceDTO("human")).build());
    newChar.setState(INIT_COMPLETE);
    newChar = charService.save(newChar);
    int orgStrength = newChar.getBaseTraits().get(STRENGTH).getCurrentValue();
    ChangeRequest change = ChangeRequest.builder()
        .changeType(ChangeType.BASE_TRAIT)
        .changeKey(STRENGTH)
        .modifier(1).build();
    ChangeRequest changeRequest = charService.increaseBasetrait(newChar.getId(), change);
    CharacterDTO result = (CharacterDTO)changeRequest.getObjectAfterChange();
    assertThat(changeRequest.getStatus()).isEqualTo(ChangeStatus.REJECTED);
    assertThat(changeRequest.getStatusLabel()).isEqualTo(ChangeStatusLabel.INSUFFICIENT_HERO_POINTS);
    assertThat(orgStrength).isEqualTo(result.getBaseTraits().get(STRENGTH).getCurrentValue());
    assertThat(result.getHeroPoints()).isZero();
  }


  @Test
  void getCharacterNonExistingRaceThrowsException(){
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").race(new RaceDTO("hobbit")).build();
    RaceNotFoundException thrown = Assertions.assertThrows(RaceNotFoundException.class, () -> {
      CharacterDTO newBeing = charService.createCharacter(testChar);
    });
  }
}
